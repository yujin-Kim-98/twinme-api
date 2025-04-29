package com.api.twinme.chat.adapter.`in`.web.mapper

import com.api.twinme.chat.adapter.`in`.web.dto.FindChatListResponse
import com.api.twinme.chat.application.result.FindChatListResult

fun FindChatListResult.toFindChatListResponse(): FindChatListResponse = FindChatListResponse(
    messages = this.messages,
    nextCursorId = this.nextCursorId
)