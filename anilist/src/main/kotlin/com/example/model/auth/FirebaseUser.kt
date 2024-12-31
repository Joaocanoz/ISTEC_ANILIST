package com.example.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class FirebaseUser(
    val email: String?,
)
