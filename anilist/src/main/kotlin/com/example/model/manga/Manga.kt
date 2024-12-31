package com.example.model.manga

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Manga(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val releaseDate: String? = null,
    val chapters: Int = 0,
    val genres: List<Genre> = emptyList(),
    val rating: Double = 0.0,
    val reviews: List<Review> = emptyList()
) {
    fun toMap(): Map<String, Any?> = mapOf(
        "id" to id,
        "title" to title,
        "description" to description,
        "releaseDate" to releaseDate,
        "chapters" to chapters,
        "genres" to genres.map { it.toMap() },
        "rating" to rating,
        "reviews" to reviews.map { it.toMap() }
    )

    companion object {
        fun fromMap(data: Map<String, Any>): Manga {
            val id = data["id"] as? String ?: UUID.randomUUID().toString()
            val title = data["title"] as String
            val description = data["description"] as String
            val releaseDate = data["releaseDate"] as? String
            val chapters = (data["chapters"] as? Number)?.toInt() ?: 0
            val genres = (data["genres"] as? List<*>)?.map {
                Genre.fromMap(it as Map<String, Any>)
            } ?: emptyList()
            val rating = (data["rating"] as? Number)?.toDouble() ?: 0.0
            val reviews = (data["reviews"] as? List<*>)?.map {
                Review.fromMap(it as Map<String, Any>)
            } ?: emptyList()

            return Manga(
                id = id,
                title = title,
                description = description,
                releaseDate = releaseDate,
                chapters = chapters,
                genres = genres,
                rating = rating,
                reviews = reviews
            )
        }
    }
}
