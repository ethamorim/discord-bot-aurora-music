package br.ethamorim.discordbot.auroramusic.redis.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("weekly-album-cycle")
class WeeklyAlbumCycle (
    @Id
    val id: Int,
    var currentMemberId: Long
)