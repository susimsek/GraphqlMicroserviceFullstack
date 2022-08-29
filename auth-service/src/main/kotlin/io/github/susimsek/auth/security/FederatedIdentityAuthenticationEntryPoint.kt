package io.github.susimsek.auth.security

import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.DefaultRedirectStrategy
import org.springframework.security.web.RedirectStrategy
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.web.util.UriComponentsBuilder
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class FederatedIdentityAuthenticationEntryPoint(
    loginPageUrl: String,
    clientRegistrationRepository: ClientRegistrationRepository
) : AuthenticationEntryPoint {

    private val redirectStrategy: RedirectStrategy = DefaultRedirectStrategy()
    private var authorizationRequestUri =
        (
            OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI +
                "/{registrationId}"
        )
    private val delegate: AuthenticationEntryPoint
    private val clientRegistrationRepository: ClientRegistrationRepository

    init {
        delegate = LoginUrlAuthenticationEntryPoint(loginPageUrl)
        this.clientRegistrationRepository = clientRegistrationRepository
    }

    @Throws(IOException::class, ServletException::class)
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authenticationException: AuthenticationException
    ) {
        val idp = request.getParameter("idp")
        if (idp != null) {
            val clientRegistration = clientRegistrationRepository.findByRegistrationId(idp)
            if (clientRegistration != null) {
                val redirectUri = UriComponentsBuilder.fromHttpRequest(ServletServerHttpRequest(request))
                    .replaceQuery(null)
                    .replacePath(authorizationRequestUri)
                    .buildAndExpand(clientRegistration.registrationId)
                    .toUriString()
                redirectStrategy.sendRedirect(request, response, redirectUri)
                return
            }
        }
        delegate.commence(request, response, authenticationException)
    }

    fun setAuthorizationRequestUri(authorizationRequestUri: String) {
        this.authorizationRequestUri = authorizationRequestUri
    }
}
