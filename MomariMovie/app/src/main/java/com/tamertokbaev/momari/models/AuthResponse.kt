package com.tamertokbaev.momari.models

data class AuthResponse(
    val message: String?,
    val token: Token?,
    val user: User?
)