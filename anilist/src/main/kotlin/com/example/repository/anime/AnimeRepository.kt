package com.example.repository.anime

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.config.FirestoreConfig
import com.example.model.anime.Anime
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Repository class to manage Anime data.
 */
class AnimeRepository {

    private val animeCollection = FirestoreConfig.getInstance().collection("animes")

    suspend fun getAllAnimes(): List<Anime> = withContext(Dispatchers.IO) {
        val documents = animeCollection.get().get()
        documents.map { Anime.fromMap(it.data) }
    }

    suspend fun getAnime(id: String): Anime? = withContext(Dispatchers.IO) {
        val document = animeCollection.document(id).get().get()
        if (document.exists()) Anime.fromMap(document.data!!) else null
    }

    suspend fun addAnime(anime: Anime): Anime = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            try {
                animeCollection.document(anime.id).set(anime.toMap()).get()
                continuation.resume(anime)
            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }
    }

    suspend fun updateAnime(id: String, anime: Anime): String? = withContext(Dispatchers.IO) {
        val document = animeCollection.document(id)
        if (document.get().get().exists()) {
            document.update(anime.toMap()).get()
            "Anime updated successfully"
        } else {
            null
        }
    }

    suspend fun deleteAnime(id: String): Boolean = withContext(Dispatchers.IO) {
        val document = animeCollection.document(id)
        if (document.get().get().exists()) {
            document.delete().get()
            true
        } else {
            false
        }
    }
}