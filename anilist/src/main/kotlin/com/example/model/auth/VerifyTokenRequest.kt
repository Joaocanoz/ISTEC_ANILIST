package com.example.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class VerifyTokenRequest(val idToken: String)

