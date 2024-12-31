package com.example.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class UserRegistrationRequest(
    val email: String,
    val password: String
)
