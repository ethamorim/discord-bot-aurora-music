package br.ethamorim.discordbot.auroramusic.redis.repository

import br.ethamorim.discordbot.auroramusic.redis.entity.GuildMember
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface GuildMemberRepository : CrudRepository<GuildMember, Long>