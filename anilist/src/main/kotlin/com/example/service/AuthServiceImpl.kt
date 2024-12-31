package com.example.service

import io.ktor.client.statement.*
import com.example.http.AuthHttpClient
import com.example.model.auth.LoginCredentials
import com.example.model.auth.VerifyTokenRequest

class AuthServiceImpl(
    private val client: AuthHttpClient,
) : AuthService {

    /**
     * Performs user login.
     *
     * @param credentials The login credentials (email and password).
     * @return The HTTP response from the authentication service.
     */
    override suspend fun login(credentials: LoginCredentials): HttpResponse {
        return client.login(credentials)
    }

    /**
     * Verifies a user's authentication token.
     *
     * @param token The token to be verified.
     * @return The HTTP response from the authentication service.
     */
    override suspend fun verifyToken(token: VerifyTokenRequest): HttpResponse {
        return client.verifyToken(token)
    }
}
