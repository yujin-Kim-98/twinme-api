package com.api.twinme.chat.adapter.out.persistence.repository

import com.api.twinme.chat.adapter.out.persistence.entity.ChatMessageEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ChatMessageJpaRepository: JpaRepository<ChatMessageEntity, Long> {

    fun findAllTop20ByUserIdAndIdLessThanOrderByCreatedAtDesc(
        userId: Long,
        id: Long?,
    ): List<ChatMessageEntity>

}