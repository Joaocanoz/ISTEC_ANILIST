package com.example.service

import io.ktor.client.statement.*
import com.example.model.auth.LoginCredentials
import com.example.model.auth.VerifyTokenRequest

/**
 * This interface defines the contract for interacting with the authentication service.
 */
interface AuthService {
    suspend fun login(credentials: LoginCredentials): HttpResponse
    suspend fun verifyToken(token: VerifyTokenRequest): HttpResponse
}
