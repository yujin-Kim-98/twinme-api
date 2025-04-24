package com.api.twinme.chat.repository

import com.api.twinme.chat.domain.ChatMessage
import org.springframework.stereotype.Repository

@Repository
class ChatMessageRepositoryImpl(
    private val chatMessageJpaRepository: ChatMessageJpaRepository
): ChatMessageRepository {

    override fun save(
        chatMessage: ChatMessage
    ): ChatMessage {
        return chatMessageJpaRepository.save(chatMessage.toEntity()).toModel()
    }

    override fun findAllTop20ByUserIdAndIdLessThanOrderByCreatedAtDesc(
        userId: Long,
        cursor: Long
    ): List<ChatMessage> {
        return chatMessageJpaRepository.findAllTop20ByUserIdAndIdLessThanOrderByCreatedAtDesc(
            userId = userId,
            cursor = cursor
        ).map { chatMessageEntity ->
            ChatMessage(
                id = chatMessageEntity.id,
                userId = chatMessageEntity.userId,
                sender = chatMessageEntity.sender,
                content = chatMessageEntity.content
            )
        }
    }

}