package io.github.susimsek.auth.security


import io.github.susimsek.auth.security.user.OAuth2UserInfoFactory
import io.github.susimsek.auth.service.MongodbUserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Component
import java.util.function.BiConsumer


@Component
class UserRepositoryOAuth2UserHandler(private val userService: MongodbUserService) : BiConsumer<String, OAuth2User> {
    override fun accept(registrationId: String, user: OAuth2User) {
        val oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, user.attributes)

        if(oAuth2UserInfo.email.isNullOrBlank()) {
            throw OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        if (userService.findByEmail(oAuth2UserInfo.email!!).isEmpty) {
            userService.registerOauth2User(oAuth2UserInfo)
        }
    }
}
