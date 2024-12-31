package com.example.controller

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.model.manga.Manga
import com.example.service.MangaService
import java.text.SimpleDateFormat
import java.util.*

/**
 * This class defines routes for managing mangas.
 *
 * @param mangaService The injected MangaService instance.
 */
class MangaController(private val mangaService: MangaService) {

    /**
     * Defines routes for mangas under the "/mangas" path.
     */
    fun Route.mangaRouting() {
        route("/mangas") {
            /**
             * GET request handler for fetching all mangas.
             */
            get {
                try {
                    val mangas = mangaService.getAllMangas()
                    call.respond(mangas)
                } catch (e: Exception) {
                    e.printStackTrace()
                    call.respond(HttpStatusCode.InternalServerError, "Failed to fetch mangas")
                }
            }

            /**
             * Protected route (requires "auth" authentication) for creating a new manga.
             */
            authenticate("auth") {
                post {
                    try {
                        val manga = call.receive<Manga>()

                        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                        formatter.timeZone = TimeZone.getTimeZone("UTC")
                        val mangaWithDate = manga.copy(releaseDate = formatter.format(Date()))

                        val savedManga = mangaService.createManga(mangaWithDate)
                        call.respond(HttpStatusCode.Created, savedManga)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        call.respond(HttpStatusCode.InternalServerError, "Failed to create manga: ${e.message}")
                    }
                }
            }

            /**
             * GET request handler for fetching a specific manga by its ID.
             */
            get("{id}") {
                try {
                    val id = call.parameters["id"] ?: return@get call.respond(
                        HttpStatusCode.BadRequest, "Missing or malformed id"
                    )

                    mangaService.getMangaById(id)?.let {
                        call.respond(it)
                    } ?: call.respond(HttpStatusCode.NotFound, "Manga not found")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "Failed to fetch manga")
                }
            }

            /**
             * Protected route (requires "auth" authentication) for updating a manga.
             */
            authenticate("auth") {
                put("{id}") {
                    try {
                        val id = call.parameters["id"] ?: return@put call.respond(
                            HttpStatusCode.BadRequest, "Missing or malformed id"
                        )
                        val manga = call.receive<Manga>()

                        mangaService.updateManga(id, manga)?.let {
                            call.respond(it)
                        } ?: call.respond(HttpStatusCode.NotFound, "Manga not found")
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.InternalServerError, "Failed to update manga")
                    }
                }
            }

            /**
             * Protected route (requires "auth" authentication) for deleting a manga.
             */
            authenticate("auth") {
                delete("{id}") {
                    try {
                        val id = call.parameters["id"] ?: return@delete call.respond(
                            HttpStatusCode.BadRequest, "Missing or malformed id"
                        )

                        if (mangaService.deleteManga(id)) {
                            call.respond(HttpStatusCode.OK, "Manga deleted")
                        } else {
                            call.respond(HttpStatusCode.NotFound, "Manga not found")
                        }
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.InternalServerError, "Failed to delete manga")
                    }
                }
            }
        }
    }
}
