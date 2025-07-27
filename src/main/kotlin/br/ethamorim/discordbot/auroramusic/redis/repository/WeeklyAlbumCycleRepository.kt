package br.ethamorim.discordbot.auroramusic.redis.repository

import br.ethamorim.discordbot.auroramusic.redis.entity.WeeklyAlbumCycle
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface WeeklyAlbumCycleRepository : CrudRepository<WeeklyAlbumCycle, Int>