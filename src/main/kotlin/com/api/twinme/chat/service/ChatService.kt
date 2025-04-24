package com.api.twinme.chat.service

import com.api.twinme.chat.repository.ChatMessageRepository
import com.api.twinme.chat.rest.dto.ChatRequest
import com.api.twinme.chat.rest.dto.GetChatResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatService(
    private val chatMessagePersistService: ChatMessagePersistService,
    private val chatMessageRepository: ChatMessageRepository
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun chat(
        request: ChatRequest,
        userId: Long
    ) {
        // TODO : MCP 응답
        val chatMessage = chatMessagePersistService.createByUser(
            content = request.message,
            userId = userId
        )

        logger.info("chat id : ${chatMessage.id}")

    }

    fun getChat(
        cursor: Long?,
        userId: Long
    ): GetChatResponse {
        val messages = chatMessageRepository.findAllTop20ByUserIdAndIdLessThanOrderByCreatedAtDesc(
            userId = userId,
            cursor = cursor ?: 20
        )
        val nextCursor = messages.lastOrNull()?.id
        return GetChatResponse(messages, nextCursor)
    }

}