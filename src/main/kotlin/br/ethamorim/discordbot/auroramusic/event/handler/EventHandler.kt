package br.ethamorim.discordbot.auroramusic.event.handler

import discord4j.core.event.domain.Event
import org.reactivestreams.Publisher

interface EventHandler<T : Event> {
    fun handle(event: T): Publisher<Any>
}