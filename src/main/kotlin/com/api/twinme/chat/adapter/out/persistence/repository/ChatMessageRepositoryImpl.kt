package com.api.twinme.chat.adapter.out.persistence.repository

import com.api.twinme.chat.domain.model.ChatMessage
import com.api.twinme.chat.domain.port.out.ChatMessageRepository
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
        cursor: Long?
    ): List<ChatMessage> {
        return chatMessageJpaRepository
            .findAllTop20ByUserIdAndIdLessThanOrderByCreatedAtDesc(userId, cursor)
            .map { it.toModel() }
    }

}