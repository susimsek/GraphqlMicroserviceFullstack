package io.github.susimsek.auth.controller.scope

data class ScopeWithDescription (val scope: String) {
        val description: String = scopeDescriptions.getOrDefault(scope, DEFAULT_DESCRIPTION)

        companion object {
            private const val DEFAULT_DESCRIPTION =
                "UNKNOWN SCOPE - We cannot provide information about this permission, use caution when granting this."
            private val scopeDescriptions: MutableMap<String, String> = HashMap()

            init {
                scopeDescriptions["profile"] = "This application will be able to read your profile information."
                scopeDescriptions["other.scope"] =
                    "This is another scope example of a scope description."
            }
        }
    }