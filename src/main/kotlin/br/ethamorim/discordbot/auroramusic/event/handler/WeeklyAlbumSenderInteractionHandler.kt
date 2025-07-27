package br.ethamorim.discordbot.auroramusic.event.handler

import br.ethamorim.discordbot.auroramusic.redis.entity.GuildMember
import br.ethamorim.discordbot.auroramusic.redis.entity.WeeklyAlbumCycle
import br.ethamorim.discordbot.auroramusic.redis.repository.GuildMemberRepository
import br.ethamorim.discordbot.auroramusic.redis.repository.WeeklyAlbumCycleRepository
import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.channel.TextChannel
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.NoSuchElementException

@Component
class WeeklyAlbumSenderInteractionHandler(
    val weeklyAlbumCycleRepository: WeeklyAlbumCycleRepository,
    val guildMemberRepository: GuildMemberRepository
) : TextInteractionHandler {

    private final var messages = listOf("This is a test! Time for <@%s> to send the album!");

    override fun handle(mediator: TextChannel): Mono<Message> {
        val guildMembers = guildMemberRepository.findAll()

        var cycleInfo: WeeklyAlbumCycle
        try {
            cycleInfo = weeklyAlbumCycleRepository.findAll().first()

            val currentMember = guildMembers.first { it.id == cycleInfo.currentMemberId }
            val currentOrder = currentMember.weeklyAlbumOrder

            var nextMember: GuildMember
            try {
                nextMember = guildMembers.first { it.weeklyAlbumOrder == currentOrder + 1 }
            } catch (_: NoSuchElementException) {
                nextMember = guildMembers.first { it.weeklyAlbumOrder == 0 }
            }
            cycleInfo.currentMemberId = nextMember.id
        } catch (_: NoSuchElementException) {
            val memberToStart = guildMembers.first { it.weeklyAlbumOrder == 0 }
            cycleInfo = WeeklyAlbumCycle(1, memberToStart.id)
        }
        weeklyAlbumCycleRepository.save(cycleInfo)

        return mediator.createMessage(String.format(messages[0], cycleInfo.currentMemberId))
    }
}