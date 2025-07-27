package br.ethamorim.discordbot.auroramusic.redis.repository

import br.ethamorim.discordbot.auroramusic.redis.entity.WeeklyAlbumCycle
import org.springframework.data.repository.CrudRepository

interface WeeklyAlbumCycleRepository : CrudRepository<WeeklyAlbumCycle, Int>