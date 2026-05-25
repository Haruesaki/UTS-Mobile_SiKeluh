package com.example.sikeluh.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationItem(
    @SerialName("id")
    val id: String? = null,
    @SerialName("user_id")
    val userId: String? = null,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("created_at")
    val createdAt: String? = null
) {
    val time: String get() = createdAt ?: ""
}
