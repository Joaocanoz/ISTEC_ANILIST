package com.example.service

import com.example.model.manga.Manga
import com.example.model.manga.Genre
import com.example.model.manga.Review
import com.example.repository.manga.MangaRepository
import com.example.repository.manga.GenreRepository
import com.example.repository.manga.ReviewRepository

class MangaServiceImpl(
    private val mangaRepository: MangaRepository,
    private val genreRepository: GenreRepository,
    private val reviewRepository: ReviewRepository
) : MangaService {

    /**
     * Retrieves a list of all mangas along with their associated reviews.
     *
     * @return A list of all mangas with their reviews.
     */
    override suspend fun getAllMangas(): List<Manga> {
        // Fetch all mangas and their associated reviews
        val mangas = mangaRepository.getAllMangas()
        return mangas.map { manga ->
            val reviews = reviewRepository.getAllReviews().filter { it.mangaId == manga.id }
            manga.copy(reviews = reviews)
        }
    }

    /**
     * Retrieves a manga by its ID along with its associated reviews.
     *
     * @param id The ID of the manga to retrieve.
     * @return The requested manga with its reviews, or null if not found.
     */
    override suspend fun getMangaById(id: String): Manga? {
        // Fetch a manga by its ID and its associated reviews
        val manga = mangaRepository.getManga(id)
        manga?.let {
            val reviews = reviewRepository.getAllReviews().filter { it.mangaId == it.id }
            return it.copy(reviews = reviews)
        }
        return null
    }

    /**
     * Creates a new manga.
     *
     * @param manga The manga to be created.
     * @return The created manga.
     */
    override suspend fun createManga(manga: Manga): Manga {
        // Create a new manga
        return mangaRepository.addManga(manga)
    }

    /**
     * Updates an existing manga.
     *
     * @param id The ID of the manga to update.
     * @param manga The updated manga data.
     * @return A success message if the update was successful, null if the manga was not found.
     */
    override suspend fun updateManga(id: String, manga: Manga): String? {
        // Update an existing manga
        return mangaRepository.updateManga(id, manga)
    }

    /**
     * Deletes an existing manga.
     *
     * @param id The ID of the manga to delete.
     * @return True if the manga was deleted successfully, false otherwise.
     */
    override suspend fun deleteManga(id: String): Boolean {
        // Delete a manga by its ID
        return mangaRepository.deleteManga(id)
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
