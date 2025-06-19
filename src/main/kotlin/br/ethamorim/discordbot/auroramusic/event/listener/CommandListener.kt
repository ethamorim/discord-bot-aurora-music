package br.ethamorim.discordbot.auroramusic.event.listener

import discord4j.core.GatewayDiscordClient
import org.springframework.stereotype.Component
import br.ethamorim.discordbot.auroramusic.event.command.Command.START_ALBUM_CYCLE
import discord4j.core.event.domain.interaction.ApplicationCommandInteractionEvent
import reactor.core.publisher.Mono

@Component
class CommandListener(gatewayClient: GatewayDiscordClient) {
    init {
        gatewayClient.on(ApplicationCommandInteractionEvent::class.java) { event ->
            if (START_ALBUM_CYCLE.equalsCommandName(event.commandName))
                event.reply("The party has already started!")
            else
                Mono.empty()
        }.subscribe()
    }
}