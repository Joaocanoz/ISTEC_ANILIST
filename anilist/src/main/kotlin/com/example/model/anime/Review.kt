package com.example.model.anime

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Review(
    val id: String = UUID.randomUUID().toString(),
    val animeId: String,
    val userEmail: String,
    val content: String,
    val rating: Double,
    val date: String = Date().toString()
) {
    fun toMap(): Map<String, Any?> = mapOf(
        "id" to id,
        "animeId" to animeId,
        "userEmail" to userEmail,
        "content" to content,
        "rating" to rating,
        "date" to date
    )

    companion object {
        fun fromMap(data: Map<String, Any>): Review {
            val id = data["id"] as? String ?: UUID.randomUUID().toString()
            val animeId = data["animeId"] as String
            val userEmail = data["userEmail"] as String
            val content = data["content"] as String
            val rating = (data["rating"] as? Number)?.toDouble() ?: 0.0
            val date = data["date"] as String
            return Review(id, animeId, userEmail, content, rating, date)
        }
    }
}
