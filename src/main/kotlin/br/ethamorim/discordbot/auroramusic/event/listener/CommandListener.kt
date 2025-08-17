package br.ethamorim.discordbot.auroramusic.event.listener

import br.ethamorim.discordbot.auroramusic.event.command.Command.START_ALBUM_CYCLE
import br.ethamorim.discordbot.auroramusic.event.command.WeeklyAlbumStartCommandExecutor
import br.ethamorim.discordbot.auroramusic.util.LoggerUtil
import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.time.Duration

@Component
class CommandListener(gatewayClient: GatewayDiscordClient, weeklyAlbumStartCommandExecutor: WeeklyAlbumStartCommandExecutor) {

    private val log = LoggerUtil.getLogger(CommandListener::class.java)

    init {
        gatewayClient.on(ChatInputInteractionEvent::class.java) { event ->
            log.info("Command ${event.commandName} received. Deferring reply.")
            if (START_ALBUM_CYCLE.equalsCommandName(event.commandName)) {
                event.deferReply()
                    .then(weeklyAlbumStartCommandExecutor.execute(event))
                    .timeout(Duration.ofSeconds(15))
                    .doOnError { error ->
                        log.error("Command execution error: ${error.message}")
                        event.createFollowup("Não fala comigo agora >:(")
                            .onErrorResume { Mono.empty() }
                            .subscribe()
                    }
                    .subscribe()
            }

            Mono.empty<Void>()
        }.subscribe()
    }
}