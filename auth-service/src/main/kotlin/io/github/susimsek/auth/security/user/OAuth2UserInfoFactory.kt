package io.github.susimsek.auth.security.user

import io.github.susimsek.auth.security.FederatedIdentityProvider
import io.github.susimsek.auth.security.OAuth2AuthenticationProcessingException

class OAuth2UserInfoFactory {

    companion object {
        fun getOAuth2UserInfo(registrationId: String, attributes: Map<String, Any?>): OAuth2UserInfo {
            return if (registrationId.equals(FederatedIdentityProvider.GOOGLE.providerId, ignoreCase = true)) {
                GoogleOAuth2UserInfo(attributes)
            } else {
                throw OAuth2AuthenticationProcessingException("Sorry! Login with $registrationId is not supported yet.")
            }
        }
    }
}