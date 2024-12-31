package com.example.model.manga

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Review(
    val id: String = UUID.randomUUID().toString(),
    val mangaId: String,
    val userEmail: String,
    val content: String,
    val rating: Double,
    val date: String = Date().toString()
) {
    fun toMap(): Map<String, Any?> = mapOf(
        "id" to id,
        "mangaId" to mangaId,
        "userEmail" to userEmail,
        "content" to content,
        "rating" to rating,
        "date" to date
    )

    companion object {
        fun fromMap(data: Map<String, Any>): Review {
            val id = data["id"] as? String ?: UUID.randomUUID().toString()
            val mangaId = data["mangaId"] as String
            val userEmail = data["userEmail"] as String
            val content = data["content"] as String
            val rating = (data["rating"] as? Number)?.toDouble() ?: 0.0
            val date = data["date"] as String
            return Review(id, mangaId, userEmail, content, rating, date)
        }
    }
}
