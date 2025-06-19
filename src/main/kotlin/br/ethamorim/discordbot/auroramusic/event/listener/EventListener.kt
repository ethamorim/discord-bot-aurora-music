package br.ethamorim.discordbot.auroramusic.event.listener

import br.ethamorim.discordbot.auroramusic.event.handler.ReadyEventHandler
import discord4j.core.GatewayDiscordClient

abstract class EventListener(
    protected val gatewayClient: GatewayDiscordClient,
    protected val eventHandler: ReadyEventHandler
) {
    init {
        listen()
    }
    abstract fun listen()
}