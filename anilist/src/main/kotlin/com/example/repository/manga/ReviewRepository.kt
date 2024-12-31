package com.example.repository.manga

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.config.FirestoreConfig
import com.example.model.manga.Review
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Repository class to manage Manga reviews.
 */
class ReviewRepository {

    private val mangaReviewCollection = FirestoreConfig.getInstance().collection("manga_reviews")

    suspend fun getAllReviews(): List<Review> = withContext(Dispatchers.IO) {
        val documents = mangaReviewCollection.get().get()
        documents.map { Review.fromMap(it.data) }
    }

    suspend fun getReview(id: String): Review? = withContext(Dispatchers.IO) {
        val document = mangaReviewCollection.document(id).get().get()
        if (document.exists()) Review.fromMap(document.data!!) else null
    }

    suspend fun addReview(review: Review): Review = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->
            try {
                mangaReviewCollection.document(review.id).set(review.toMap()).get()
                continuation.resume(review)
            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }
    }

    suspend fun updateReview(id: String, review: Review): String? = withContext(Dispatchers.IO) {
        val document = mangaReviewCollection.document(id)
        if (document.get().get().exists()) {
            document.update(mapOf("content" to review.content, "rating" to review.rating)).get()
            "Manga review updated successfully"
        } else {
            null
        }
    }

    suspend fun deleteReview(id: String): Boolean = withContext(Dispatchers.IO) {
        val document = mangaReviewCollection.document(id)
        if (document.get().get().exists()) {
            document.delete().get()
            true
        } else {
            false
        }
    }
}
