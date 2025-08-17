package br.ethamorim.discordbot.auroramusic.event.handler

import br.ethamorim.discordbot.auroramusic.redis.entity.GuildMember
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

    private final var messages = listOf("Hora de <@%s> mandar o álbum!");

    override fun handle(mediator: TextChannel): Mono<Message> {
        val cycleInfo = weeklyAlbumCycleRepository.findById(0)
        if (cycleInfo.isPresent && cycleInfo.get().isOn) {
            val guildMembers = guildMemberRepository.findAll().sortedBy { it.weeklyAlbumOrder }

            val currentOrderCycle = cycleInfo.get()

            val currentMember = guildMembers.first { it.id == currentOrderCycle.currentMemberId }
            val currentOrderNumber = currentMember.weeklyAlbumOrder

            var nextMember: GuildMember
            try {
                nextMember = guildMembers.first { it.weeklyAlbumOrder == currentOrderNumber + 1 }
            } catch (_: NoSuchElementException) {
                nextMember = guildMembers.first()
            }
            val nextOrderCycle = currentOrderCycle.copy()
            nextOrderCycle.currentMemberId = nextMember.id

            weeklyAlbumCycleRepository.deleteById(0)
            weeklyAlbumCycleRepository.save(nextOrderCycle)

            return mediator.createMessage(String.format(messages[0], currentMember.id))
        }
        return Mono.empty()
    }
}