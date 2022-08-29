package io.github.susimsek.auth.security

import org.springframework.context.ApplicationContext
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.util.Assert
import java.util.function.BiConsumer
import java.util.function.Consumer

class FederatedIdentityConfigurer : AbstractHttpConfigurer<FederatedIdentityConfigurer, HttpSecurity>() {
    private var loginPageUrl = "/login"
    private var authorizationRequestUri: String? = null
    private var oauth2UserHandler: BiConsumer<String, OAuth2User>? = null
    private var oidcUserHandler: BiConsumer<String, OidcUser>? = null

    /**
     * @param loginPageUrl The URL of the login page, defaults to `"/login"`
     * @return This configurer for additional configuration
     */
    fun loginPageUrl(loginPageUrl: String): FederatedIdentityConfigurer {
        Assert.hasText(loginPageUrl, "loginPageUrl cannot be empty")
        this.loginPageUrl = loginPageUrl
        return this
    }

    /**
     * @param authorizationRequestUri The authorization request URI for initiating
     * the login flow with an external IDP, defaults to `"/oauth2/authorization/{registrationId}"`
     * @return This configurer for additional configuration
     */
    fun authorizationRequestUri(authorizationRequestUri: String): FederatedIdentityConfigurer {
        Assert.hasText(authorizationRequestUri, "authorizationRequestUri cannot be empty")
        this.authorizationRequestUri = authorizationRequestUri
        return this
    }

    /**
     * @param oauth2UserHandler The [Consumer] for performing JIT account provisioning
     * with an OAuth 2.0 IDP
     * @return This configurer for additional configuration
     */
    fun oauth2UserHandler(oauth2UserHandler: BiConsumer<String, OAuth2User>): FederatedIdentityConfigurer {
        Assert.notNull(oauth2UserHandler, "oauth2UserHandler cannot be null")
        this.oauth2UserHandler = oauth2UserHandler
        return this
    }

    /**
     * @param oidcUserHandler The [Consumer] for performing JIT account provisioning
     * with an OpenID Connect 1.0 IDP
     * @return This configurer for additional configuration
     */
    fun oidcUserHandler(oidcUserHandler: BiConsumer<String, OidcUser>): FederatedIdentityConfigurer {
        Assert.notNull(oidcUserHandler, "oidcUserHandler cannot be null")
        this.oidcUserHandler = oidcUserHandler
        return this
    }

    // @formatter:off
    @Throws(Exception::class)
    override fun init(http: HttpSecurity) {
        val applicationContext: ApplicationContext = http.getSharedObject(ApplicationContext::class.java)
        val clientRegistrationRepository: ClientRegistrationRepository = applicationContext.getBean(
            ClientRegistrationRepository::class.java
        )
        val authenticationEntryPoint =
            FederatedIdentityAuthenticationEntryPoint(loginPageUrl, clientRegistrationRepository)
        if (authorizationRequestUri != null) {
            authenticationEntryPoint.setAuthorizationRequestUri(authorizationRequestUri!!)
        }
        val authenticationSuccessHandler = FederatedIdentityAuthenticationSuccessHandler()
        if (oauth2UserHandler != null) {
            authenticationSuccessHandler.setOAuth2UserHandler(oauth2UserHandler!!)
        }
        if (oidcUserHandler != null) {
            authenticationSuccessHandler.setOidcUserHandler(oidcUserHandler!!)
        }
        http
            .exceptionHandling { exceptionHandling: ExceptionHandlingConfigurer<HttpSecurity> ->
                exceptionHandling.authenticationEntryPoint(
                    authenticationEntryPoint
                )
            }
            .oauth2Login { oauth2Login: OAuth2LoginConfigurer<HttpSecurity> ->
                oauth2Login.successHandler(authenticationSuccessHandler)
                if (authorizationRequestUri != null) {
                    val baseUri = authorizationRequestUri!!.replace("/{registrationId}", "")
                    oauth2Login.authorizationEndpoint { authorizationEndpoint ->
                        authorizationEndpoint.baseUri(
                            baseUri
                        )
                    }
                }
            }
    } // @formatter:on
}
