package com.api.twinme.chat.repository

import com.api.twinme.chat.domain.ChatMessage

interface ChatMessageRepository {

    fun save(
        chatMessage: ChatMessage
    ): ChatMessage

    fun findAllTop20ByUserIdAndIdLessThanOrderByCreatedAtDesc(
        userId: Long,
        cursor: Long
    ): List<ChatMessage>

}