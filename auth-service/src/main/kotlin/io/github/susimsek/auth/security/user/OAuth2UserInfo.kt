package io.github.susimsek.auth.security.user

abstract class OAuth2UserInfo(var attributes: Map<String, Any?>) {
    abstract val id: String?
    abstract val name: String?
    abstract val email: String?
    abstract val imageUrl: String?
}
