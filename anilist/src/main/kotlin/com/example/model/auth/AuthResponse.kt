package com.example.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val idToken: String
)
