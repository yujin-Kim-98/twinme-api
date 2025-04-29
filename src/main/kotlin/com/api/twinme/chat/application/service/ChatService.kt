package com.api.twinme.chat.application.service

import com.api.twinme.chat.domain.port.out.ChatMessageRepository
import com.api.twinme.chat.adapter.`in`.web.dto.GetChatResponse
import com.api.twinme.chat.application.command.FindChatMessageCommand
import com.api.twinme.chat.application.command.SendChatCommand
import com.api.twinme.chat.application.command.toModel
import com.api.twinme.chat.application.result.FindChatListResult
import com.api.twinme.chat.application.usecase.ChatUseCase
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatService(
    private val chatMessageRepository: ChatMessageRepository
): ChatUseCase {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    override fun sendChat(
        command: SendChatCommand,
    ) {
        chatMessageRepository.save(command.toModel())
    }

    override fun findChatByCursor(
        command: FindChatMessageCommand
    ): FindChatListResult {
        val messages = chatMessageRepository.findAllTop20ByUserIdAndIdLessThanOrderByCreatedAtDesc(
            userId = command.userId,
            cursor = command.cursor
        )
        return FindChatListResult(messages, messages.lastOrNull()?.id)
    }

}