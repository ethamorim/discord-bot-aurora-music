package br.ethamorim.discordbot.auroramusic.event.listener

import br.ethamorim.discordbot.auroramusic.event.command.Command.START_ALBUM_CYCLE
import br.ethamorim.discordbot.auroramusic.event.command.WeeklyAlbumStartCommandExecutor
import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class CommandListener(gatewayClient: GatewayDiscordClient, weeklyAlbumStartCommandExecutor: WeeklyAlbumStartCommandExecutor) {
    init {
        gatewayClient.on(ChatInputInteractionEvent::class.java) { event ->
            if (START_ALBUM_CYCLE.equalsCommandName(event.commandName))
                event.deferReply()
                    .then(weeklyAlbumStartCommandExecutor.execute(event))
            else
                Mono.empty<Void>()
        }.subscribe()
    }
}