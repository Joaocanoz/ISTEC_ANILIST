package com.example.service

import com.example.model.anime.Anime
import com.example.model.anime.Genre
import com.example.model.anime.Review

/**
 * This interface defines the contract for interacting with anime-related services.
 */
interface AnimeService {

    // Anime Operations
    suspend fun getAllAnimes(): List<Anime>
    suspend fun getAnimeById(id: String): Anime?
    suspend fun createAnime(anime: Anime): Anime
    suspend fun updateAnime(id: String, anime: Anime): String?
    suspend fun deleteAnime(id: String): Boolean

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
