package br.ethamorim.discordbot.auroramusic.event.command

import br.ethamorim.discordbot.auroramusic.exception.InvalidOptionException
import br.ethamorim.discordbot.auroramusic.redis.entity.GuildMember
import br.ethamorim.discordbot.auroramusic.redis.entity.WeeklyAlbumCycle
import br.ethamorim.discordbot.auroramusic.redis.repository.GuildMemberRepository
import br.ethamorim.discordbot.auroramusic.redis.repository.WeeklyAlbumCycleRepository
import br.ethamorim.discordbot.auroramusic.util.LoggerUtil
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.util.*

@Component
class WeeklyAlbumStartCommandExecutor(
    private val guildMemberRepository: GuildMemberRepository,
    private val weeklyAlbumCycleRepository: WeeklyAlbumCycleRepository
) : CommandExecutor<ChatInputInteractionEvent> {

    private val log = LoggerUtil.getLogger(WeeklyAlbumStartCommandExecutor::class.java)

    override fun execute(event: ChatInputInteractionEvent): Mono<Void> {
        return Mono.fromCallable { processNextMemberInteration(event) }
            .subscribeOn(Schedulers.boundedElastic())
            .flatMap { nickname ->
                event.createFollowup("Tá bom! Começarei a contar o ciclo a partir de $nickname :)")
            }
            .onErrorResume { error ->
                val errorMessage = error.message!!
                log.error("Error trying to start a new cycle: $errorMessage")

                event.createFollowup("Não estou me sentindo muito bem :(")
            }
            .then()
    }

    private fun processNextMemberInteration(event: ChatInputInteractionEvent): String {
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
        log.info("New cycle start saved succesfully")

        return guildMember.nickname
    }

    private fun findByWeeklyAlbumOrder(order: Int): Optional<GuildMember> {
        val member = guildMemberRepository.findAll().filter { it.weeklyAlbumOrder == order }
        if (member.isEmpty())
            return Optional.empty()
        return Optional.of(member.first())
    }
}