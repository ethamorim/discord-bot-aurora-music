package br.ethamorim.discordbot.auroramusic.redis.repository

import br.ethamorim.discordbot.auroramusic.redis.entity.GuildMember
import org.springframework.data.repository.CrudRepository

interface GuildMemberRepository : CrudRepository<GuildMember, Long>