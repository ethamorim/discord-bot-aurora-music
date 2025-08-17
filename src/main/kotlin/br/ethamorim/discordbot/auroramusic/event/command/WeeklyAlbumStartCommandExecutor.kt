package br.ethamorim.discordbot.auroramusic.event.command

import br.ethamorim.discordbot.auroramusic.exception.InvalidOptionException
import br.ethamorim.discordbot.auroramusic.redis.entity.GuildMember
import br.ethamorim.discordbot.auroramusic.redis.entity.WeeklyAlbumCycle
import br.ethamorim.discordbot.auroramusic.redis.repository.GuildMemberRepository
import br.ethamorim.discordbot.auroramusic.redis.repository.WeeklyAlbumCycleRepository
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import discord4j.core.spec.InteractionFollowupCreateMono
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.*

@Component
class WeeklyAlbumStartCommandExecutor(
    private val guildMemberRepository: GuildMemberRepository,
    private val weeklyAlbumCycleRepository: WeeklyAlbumCycleRepository
) : CommandExecutor<ChatInputInteractionEvent> {
    override fun execute(event: ChatInputInteractionEvent): Mono<Void> {
        try {
            val orderToStart = event.getOption("order")
                .flatMap { it.value }
                .map {
                    val value = it.asString()
                    value.toInt()
                }
                .orElseThrow { throw InvalidOptionException("Ordem inválida!") }

            val guildMember = findByWeeklyAlbumOrder(orderToStart)
                .orElseThrow { throw InvalidOptionException("Ordem inválida!") }

            val cycleOptional = weeklyAlbumCycleRepository.findById(0)
            val cycle = if (cycleOptional.isPresent) {
                val c = cycleOptional.get().copy()
                weeklyAlbumCycleRepository.deleteById(0)

                c.currentMemberId = guildMember.id
                c.isOn = true
                c
            } else {
                WeeklyAlbumCycle(0, guildMember.id, true)
            }
            weeklyAlbumCycleRepository.save(cycle)

            return event.createFollowup("Começarei a contar o ciclo a partir de ${guildMember.nickname}")
                .then()
        } catch (e: InvalidOptionException) {
            return event.createFollowup(e.message!!)
                .then()
        }
    }

    fun findByWeeklyAlbumOrder(order: Int): Optional<GuildMember> {
        val member = guildMemberRepository.findAll().filter { it.weeklyAlbumOrder == order }
        if (member.isEmpty())
            return Optional.empty()
        return Optional.of(member.first())
    }
}