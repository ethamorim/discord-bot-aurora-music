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
    private val weeklyAlbumCycleRepository: WeeklyAlbumCycleRepository,
    private val guildMemberRepository: GuildMemberRepository
) : TextInteractionHandler {

    private final var messages = listOf("This is a test! Time for %s to send the album!");

    override fun handle(mediator: TextChannel): Mono<Message> {
        val cycleInfo = weeklyAlbumCycleRepository.findById(0)
        if (cycleInfo.isPresent && cycleInfo.get().isOn) {
            val guildMembers = guildMemberRepository.findAll().sortedBy { it.weeklyAlbumOrder }

            val order = cycleInfo.get()

            val currentMember = guildMembers.first { it.id == order.currentMemberId }
            val currentOrder = currentMember.weeklyAlbumOrder

            var nextMember: GuildMember
            try {
                nextMember = guildMembers.first { it.weeklyAlbumOrder == currentOrder + 1 }
            } catch (_: NoSuchElementException) {
                nextMember = guildMembers.first()
            }
            order.currentMemberId = nextMember.id
            weeklyAlbumCycleRepository.save(order)

            return mediator.createMessage(String.format(messages[0], order.currentMemberId))
        }
        return Mono.empty()
    }
}