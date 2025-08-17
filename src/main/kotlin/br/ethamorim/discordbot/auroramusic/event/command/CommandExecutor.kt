package br.ethamorim.discordbot.auroramusic.event.command

import discord4j.core.event.domain.interaction.ApplicationCommandInteractionEvent
import reactor.core.publisher.Mono

interface CommandExecutor<I : ApplicationCommandInteractionEvent> {
    fun  execute(event: I): Mono<Void>
}