package br.ethamorim.discordbot.auroramusic.redis.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("weeklyAlbumCycle")
data class WeeklyAlbumCycle (
    @Id
    var id: Int,
    var currentMemberId: Long,
    var isOn: Boolean
)