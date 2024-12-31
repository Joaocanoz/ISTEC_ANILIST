package com.example.controller

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.model.anime.Anime
import com.example.service.AnimeService
import java.text.SimpleDateFormat
import java.util.*

/**
 * This class defines routes for managing animes.
 *
 * @param animeService The injected AnimeService instance.
 */
class AnimeController(private val animeService: AnimeService) {

    /**
     * Defines routes for animes under the "/animes" path.
     */
    fun Route.animeRouting() {
        route("/animes") {
            /**
             * GET request handler for fetching all animes.
             */
            get {
                try {
                    val animes = animeService.getAllAnimes()
                    call.respond(animes)
                } catch (e: Exception) {
                    e.printStackTrace()
                    call.respond(HttpStatusCode.InternalServerError, "Failed to fetch animes")
                }
            }

            /**
             * Protected route (requires "auth" authentication) for creating a new anime.
             */
            authenticate("auth") {
                post {
                    try {
                        val anime = call.receive<Anime>()

                        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                        formatter.timeZone = TimeZone.getTimeZone("UTC")
                        val animeWithDate = anime.copy(releaseDate = formatter.format(Date()))

                        val savedAnime = animeService.createAnime(animeWithDate)
                        call.respond(HttpStatusCode.Created, savedAnime)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        call.respond(HttpStatusCode.InternalServerError, "Failed to create anime: ${e.message}")
                    }
                }
            }

            /**
             * GET request handler for fetching a specific anime by its ID.
             */
            get("{id}") {
                try {
                    val id = call.parameters["id"] ?: return@get call.respond(
                        HttpStatusCode.BadRequest, "Missing or malformed id"
                    )

                    animeService.getAnimeById(id)?.let {
                        call.respond(it)
                    } ?: call.respond(HttpStatusCode.NotFound, "Anime not found")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "Failed to fetch anime")
                }
            }

            /**
             * Protected route (requires "auth" authentication) for updating an anime.
             */
            authenticate("auth") {
                put("{id}") {
                    try {
                        val id = call.parameters["id"] ?: return@put call.respond(
                            HttpStatusCode.BadRequest, "Missing or malformed id"
                        )
                        val anime = call.receive<Anime>()

                        animeService.updateAnime(id, anime)?.let {
                            call.respond(it)
                        } ?: call.respond(HttpStatusCode.NotFound, "Anime not found")
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.InternalServerError, "Failed to update anime")
                    }
                }
            }

            /**
             * Protected route (requires "auth" authentication) for deleting an anime.
             */
            authenticate("auth") {
                delete("{id}") {
                    try {
                        val id = call.parameters["id"] ?: return@delete call.respond(
                            HttpStatusCode.BadRequest, "Missing or malformed id"
                        )

                        if (animeService.deleteAnime(id)) {
                            call.respond(HttpStatusCode.OK, "Anime deleted")
                        } else {
                            call.respond(HttpStatusCode.NotFound, "Anime not found")
                        }
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.InternalServerError, "Failed to delete anime")
                    }
                }
            }
        }
    }
}
