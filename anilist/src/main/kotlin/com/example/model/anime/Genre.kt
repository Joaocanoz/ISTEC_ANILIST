package com.example.model.anime

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Genre(
    val id: String = UUID.randomUUID().toString(),
    val name: String
) {
    fun toMap(): Map<String, Any?> = mapOf(
        "id" to id,
        "name" to name
    )

    companion object {
        fun fromMap(data: Map<String, Any>): Genre {
            val id = data["id"] as? String ?: UUID.randomUUID().toString()
            val name = data["name"] as String
            return Genre(id, name)
        }
    }
}
