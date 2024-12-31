package com.example.service

import com.example.model.manga.Manga
import com.example.model.manga.Genre
import com.example.model.manga.Review

/**
 * This interface defines the contract for interacting with manga-related services.
 */
interface MangaService {

    // Manga Operations
    suspend fun getAllMangas(): List<Manga>
    suspend fun getMangaById(id: String): Manga?
    suspend fun createManga(manga: Manga): Manga
    suspend fun updateManga(id: String, manga: Manga): String?
    suspend fun deleteManga(id: String): Boolean

    // Genre Operations
    suspend fun getAllGenres(): List<Genre>
    suspend fun getGenreById(id: String): Genre?
    suspend fun createGenre(genre: Genre): Genre
    suspend fun updateGenre(id: String, genre: Genre): String?
    suspend fun deleteGenre(id: String): Boolean

    // Review Operations
    suspend fun getAllReviews(): List<Review>
    suspend fun getReviewById(id: String): Review?
    suspend fun createReview(review: Review): Review
    suspend fun updateReview(id: String, review: Review): String?
    suspend fun deleteReview(id: String): Boolean
}
