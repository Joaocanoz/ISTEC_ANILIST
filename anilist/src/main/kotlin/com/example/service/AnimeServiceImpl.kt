package com.example.service

import com.example.model.anime.Anime
import com.example.model.anime.Genre
import com.example.model.anime.Review
import com.example.repository.anime.AnimeRepository
import com.example.repository.anime.GenreRepository
import com.example.repository.anime.ReviewRepository

class AnimeServiceImpl(
    private val animeRepository: AnimeRepository,
    private val genreRepository: GenreRepository,
    private val reviewRepository: ReviewRepository
) : AnimeService {

    /**
     * Retrieves a list of all animes.
     *
     * @return A list of all animes.
     */
    override suspend fun getAllAnimes(): List<Anime> {
        // Fetch all animes and their associated reviews
        val animes = animeRepository.getAllAnimes()
        return animes.map { anime ->
            val reviews = reviewRepository.getAllReviews().filter { it.animeId == anime.id }
            anime.copy(reviews = reviews)
        }
    }

    /**
     * Retrieves an anime by its ID.
     *
     * @param id The ID of the anime to retrieve.
     * @return The requested anime, or null if not found.
     */
    override suspend fun getAnimeById(id: String): Anime? {
        // Fetch an anime by its ID and its associated reviews
        val anime = animeRepository.getAnime(id)
        anime?.let {
            val reviews = reviewRepository.getAllReviews().filter { it.animeId == it.id }
            return it.copy(reviews = reviews)
        }
        return null
    }

    /**
     * Creates a new anime.
     *
     * @param anime The anime to be created.
     * @return The created anime.
     */
    override suspend fun createAnime(anime: Anime): Anime {
        // Create a new anime
        return animeRepository.addAnime(anime)
    }

    /**
     * Updates an existing anime.
     *
     * @param id The ID of the anime to update.
     * @param anime The updated anime data.
     * @return A success message if the update was successful, null if the anime was not found.
     */
    override suspend fun updateAnime(id: String, anime: Anime): String? {
        // Update an existing anime
        return animeRepository.updateAnime(id, anime)
    }

    /**
     * Deletes an existing anime.
     *
     * @param id The ID of the anime to delete.
     * @return True if the anime was deleted successfully, false otherwise.
     */
    override suspend fun deleteAnime(id: String): Boolean {
        // Delete an anime by its ID
        return animeRepository.deleteAnime(id)
    }

    /**
     * Retrieves a list of all genres.
     *
     * @return A list of all genres.
     */
    override suspend fun getAllGenres(): List<Genre> {
        // Fetch all genres
        return genreRepository.getAllGenres()
    }

    /**
     * Retrieves a genre by its ID.
     *
     * @param id The ID of the genre to retrieve.
     * @return The requested genre, or null if not found.
     */
    override suspend fun getGenreById(id: String): Genre? {
        // Fetch a genre by its ID
        return genreRepository.getGenre(id)
    }

    /**
     * Creates a new genre.
     *
     * @param genre The genre to be created.
     * @return The created genre.
     */
    override suspend fun createGenre(genre: Genre): Genre {
        // Create a new genre
        return genreRepository.addGenre(genre)
    }

    /**
     * Updates an existing genre.
     *
     * @param id The ID of the genre to update.
     * @param genre The updated genre data.
     * @return A success message if the update was successful, null if the genre was not found.
     */
    override suspend fun updateGenre(id: String, genre: Genre): String? {
        // Update an existing genre
        return genreRepository.updateGenre(id, genre)
    }

    /**
     * Deletes an existing genre.
     *
     * @param id The ID of the genre to delete.
     * @return True if the genre was deleted successfully, false otherwise.
     */
    override suspend fun deleteGenre(id: String): Boolean {
        // Delete a genre by its ID
        return genreRepository.deleteGenre(id)
    }

    /**
     * Retrieves a list of all reviews.
     *
     * @return A list of all reviews.
     */
    override suspend fun getAllReviews(): List<Review> {
        // Fetch all reviews
        return reviewRepository.getAllReviews()
    }

    /**
     * Retrieves a review by its ID.
     *
     * @param id The ID of the review to retrieve.
     * @return The requested review, or null if not found.
     */
    override suspend fun getReviewById(id: String): Review? {
        // Fetch a review by its ID
        return reviewRepository.getReview(id)
    }

    /**
     * Creates a new review.
     *
     * @param review The review to be created.
     * @return The created review.
     */
    override suspend fun createReview(review: Review): Review {
        // Create a new review
        return reviewRepository.addReview(review)
    }

    /**
     * Updates an existing review.
     *
     * @param id The ID of the review to update.
     * @param review The updated review data.
     * @return A success message if the update was successful, null if the review was not found.
     */
    override suspend fun updateReview(id: String, review: Review): String? {
        // Update an existing review
        return reviewRepository.updateReview(id, review)
    }

    /**
     * Deletes an existing review.
     *
     * @param id The ID of the review to delete.
     * @return True if the review was deleted successfully, false otherwise.
     */
    override suspend fun deleteReview(id: String): Boolean {
        // Delete a review by its ID
        return reviewRepository.deleteReview(id)
    }
}
