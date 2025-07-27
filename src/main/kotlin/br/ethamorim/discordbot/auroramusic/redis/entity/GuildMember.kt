package br.ethamorim.discordbot.auroramusic.redis.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("guildMember")
data class GuildMember(
    @Id
    val id: Long,
    val nickname: String,
    val weeklyAlbumOrder: Int
)