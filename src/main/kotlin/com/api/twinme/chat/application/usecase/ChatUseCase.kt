package com.api.twinme.chat.application.usecase

import com.api.twinme.chat.application.command.FindChatMessageCommand
import com.api.twinme.chat.application.command.SendChatCommand
import com.api.twinme.chat.application.result.FindChatListResult

interface ChatUseCase {

    fun sendChat(
        command: SendChatCommand
    )

    fun findChatByCursor(
        command: FindChatMessageCommand
    ): FindChatListResult

}