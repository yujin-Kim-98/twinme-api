package com.api.twinme.chat.domain.model

data class ChatMessage(
    val id: Long? = null,
    val userId: Long,
    val sender: Sender,
    val content: String
)

enum class Sender {
    USER,
    BOT
}