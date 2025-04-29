package com.api.twinme.chat.domain.port.out

import com.api.twinme.chat.domain.model.ChatMessage

interface ChatMessageRepository {

    fun save(
        chatMessage: ChatMessage
    ): ChatMessage

    fun findAllTop20ByUserIdAndIdLessThanOrderByCreatedAtDesc(
        userId: Long,
        cursor: Long?
    ): List<ChatMessage>

}