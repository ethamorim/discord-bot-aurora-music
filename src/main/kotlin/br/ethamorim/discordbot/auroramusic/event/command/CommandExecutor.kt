package br.ethamorim.discordbot.auroramusic.event.command

import discord4j.core.event.domain.interaction.ApplicationCommandInteractionEvent
import discord4j.core.`object`.entity.Message
import reactor.core.publisher.Mono

interface CommandExecutor<I : ApplicationCommandInteractionEvent, out O : Mono<Message>> {
    fun  execute(event: I): O
}