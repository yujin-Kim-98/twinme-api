package com.api.twinme.chat.service

import com.api.twinme.chat.domain.ChatMessage
import com.api.twinme.chat.entity.ChatMessageEntity
import com.api.twinme.chat.entity.ChatSender
import com.api.twinme.chat.repository.ChatMessageRepository
import org.springframework.stereotype.Service

@Service
class ChatMessagePersistService(
    private val chatMessageRepository: ChatMessageRepository
) {

    fun createByUser(
        content: String,
        userId: Long
    ): ChatMessage {
        val chatMessage = ChatMessage(
            userId = userId,
            sender = ChatSender.USER,
            content = content
        )
        return chatMessageRepository.save(chatMessage)
    }

}