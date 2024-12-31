package com.example

import io.ktor.client.call.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*
import io.ktor.util.*
import kotlinx.coroutines.runBlocking
import com.example.config.FirestoreConfig
import com.example.controller.AnimeController
import com.example.controller.AuthController
import com.example.controller.MangaController
import com.example.http.AuthHttpClient
import com.example.model.auth.FirebaseUser
import com.example.model.auth.VerifyTokenRequest
import com.example.model.auth.VerifyTokenResponse
import com.example.repository.anime.AnimeRepository
import com.example.repository.manga.GenreRepository
import com.example.repository.manga.MangaRepository
import com.example.repository.manga.ReviewRepository
import com.example.service.*

/**
 * Entry point for the Noticias application.
 */
fun main() {
    FirestoreConfig.initialize("anime-list.json")

    embeddedServer(Netty, port = 8080) {
        module()
    }.start(wait = true)
}

/**
 * Configures the Ktor application modules.
 *
 * @receiver The Ktor application instance.
 */
fun Application.module() {

    // Initialize Repositories
    val mangaRepository = MangaRepository()
    val mangaGenreRepository = GenreRepository()
    val mangaReviewRepository = ReviewRepository()
    val animeRepository = AnimeRepository()
    val animeGenreRepository = com.example.repository.anime.GenreRepository()
    val animeReviewRepository = com.example.repository.anime.ReviewRepository()

    // Initialize Services
    val mangaService = MangaServiceImpl(mangaRepository, mangaGenreRepository, mangaReviewRepository)
    val animeService = AnimeServiceImpl(animeRepository, animeGenreRepository, animeReviewRepository)

    // Initialize Controllers
    val mangaController = MangaController(mangaService)
    val animeController = AnimeController(animeService)

    // Initialize Auth Service
    val authClient = AuthHttpClient()
    val authService = AuthServiceImpl(authClient)
    val authController = AuthController(authService)

    install(ContentNegotiation) {
        json()
    }

    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.AccessControlAllowOrigin)
        allowCredentials = true
        anyHost()
    }

    // Authentication setup
    install(Authentication) {
        basic("auth") {
            skipWhen { call ->
                val token = call.request.headers["Authorization"]?.removePrefix("Bearer ")
                if (token == null) {
                    return@skipWhen false
                }

                try {
                    val isValid = runBlocking {
                        try {
                            val httpResponse = authService.verifyToken(VerifyTokenRequest(token))
                            val responseText = httpResponse.body<VerifyTokenResponse>()

                            if (responseText.users.isNotEmpty()) {
                                // Extract user information and store it in the call's attributes
                                val user = responseText.users.first()
                                call.attributes.put(
                                    AttributeKey("firebase_user"),
                                    FirebaseUser(
                                        email = user.email
                                    )
                                )
                                return@runBlocking true
                            }
                            return@runBlocking false
                        } catch (e: Exception) {
                            println("Token verification failed: ${e.message}")
                            return@runBlocking false
                        }
                    }
                    return@skipWhen isValid
                } catch (e: Exception) {
                    println("Error during token extraction or validation: ${e.message}")
                    return@skipWhen false
                }
            }
        }
    }

    // Routing setup
    routing {
        // Configure Swagger UI
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")

        with(authController) {
            authRouting()
        }

        with(mangaController) {
            mangaRouting()
        }

        with(animeController) {
            animeRouting()
        }
    }
}
