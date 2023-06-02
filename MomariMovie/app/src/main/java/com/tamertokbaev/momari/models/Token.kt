package com.tamertokbaev.momari.models

data class Token(
    val access_token: String?,
    val expires_in: Int?,
    val token_type: String?
)