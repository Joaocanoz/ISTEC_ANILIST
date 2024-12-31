package com.example.repository.manga

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.config.FirestoreConfig
import com.example.model.manga.Manga
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Repository class to manage Manga data.
 */
class MangaRepository {

    private val mangaCollection = FirestoreConfig.getInstance().collection("mangas")

    suspend fun getAllMangas(): List<Manga> = withContext(Dispatchers.IO) {
        val documents = mangaCollection.get().get()
        documents.map { Manga.fromMap(it.data) }
    }

    suspend fun getManga(id: String): Manga? = withContext(Dispatchers.IO) {
        val document = mangaCollection.document(id).get().get()
        if (document.exists()) Manga.fromMap(document.data!!) else null
    }

    suspend fun addManga(manga: Manga): Manga = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            try {
                mangaCollection.document(manga.id).set(manga.toMap()).get()
                continuation.resume(manga)
            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }
    }

    suspend fun updateManga(id: String, manga: Manga): String? = withContext(Dispatchers.IO) {
        val document = mangaCollection.document(id)
        if (document.get().get().exists()) {
            document.update(manga.toMap()).get()
            "Manga updated successfully"
        } else {
            null
        }
    }

    suspend fun deleteManga(id: String): Boolean = withContext(Dispatchers.IO) {
        val document = mangaCollection.document(id)
        if (document.get().get().exists()) {
            document.delete().get()
            true
        } else {
            false
        }
    }
}
