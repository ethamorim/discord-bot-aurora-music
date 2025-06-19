package br.ethamorim.discordbot.auroramusic.event.listener

import br.ethamorim.discordbot.auroramusic.event.handler.EventHandler
import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.Event

abstract class EventListener(
    protected val gatewayClient: GatewayDiscordClient,
    protected val eventHandler: EventHandler<Event>
) {
    init {
        listen()
    }
    abstract fun listen()
}