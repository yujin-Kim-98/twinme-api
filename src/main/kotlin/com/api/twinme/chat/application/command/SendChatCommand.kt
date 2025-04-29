package com.api.twinme.chat.application.command

import com.api.twinme.chat.domain.model.ChatMessage
import com.api.twinme.chat.domain.model.Sender

data class SendChatCommand(
    val message: String,
    val userId: Long
)

fun SendChatCommand.toModel(): ChatMessage = ChatMessage(
    userId = this.userId,
    sender = Sender.USER,
    content = this.message
)