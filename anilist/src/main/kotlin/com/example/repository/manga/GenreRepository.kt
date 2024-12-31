package com.example.repository.manga

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.config.FirestoreConfig
import com.example.model.manga.Genre
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Repository class to manage Manga genres.
 */
class GenreRepository {

    private val genreCollection = FirestoreConfig.getInstance().collection("manga_genres")

    suspend fun getAllGenres(): List<Genre> = withContext(Dispatchers.IO) {
        val documents = genreCollection.get().get()
        documents.map { Genre.fromMap(it.data) }
    }

    suspend fun getGenre(id: String): Genre? = withContext(Dispatchers.IO) {
        val document = genreCollection.document(id).get().get()
        if (document.exists()) Genre.fromMap(document.data!!) else null
    }

    suspend fun addGenre(genre: Genre): Genre = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            try {
                genreCollection.document(genre.id).set(genre.toMap()).get()
                continuation.resume(genre)
            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }
    }

    suspend fun updateGenre(id: String, genre: Genre): String? = withContext(Dispatchers.IO) {
        val document = genreCollection.document(id)
        if (document.get().get().exists()) {
            document.update(genre.toMap()).get()
            "Genre updated successfully"
        } else {
            null
        }
    }

    suspend fun deleteGenre(id: String): Boolean = withContext(Dispatchers.IO) {
        val document = genreCollection.document(id)
        if (document.get().get().exists()) {
            document.delete().get()
            true
        } else {
            false
        }
    }
}
