package com.api.twinme.chat.domain

import com.api.twinme.chat.entity.ChatMessageEntity
import com.api.twinme.chat.entity.ChatSender

data class ChatMessage(
    val id: Long? = null,
    val userId: Long,
    val sender: ChatSender,
    val content: String
){
    fun toEntity() = ChatMessageEntity(
        id = null,
        userId = userId,
        sender = sender,
        content = content
    )
}