package com.api.twinme.chat.application.result

import com.api.twinme.chat.domain.model.ChatMessage

data class FindChatListResult(
    val messages: List<ChatMessage>,
    val nextCursorId: Long? = 0L
)