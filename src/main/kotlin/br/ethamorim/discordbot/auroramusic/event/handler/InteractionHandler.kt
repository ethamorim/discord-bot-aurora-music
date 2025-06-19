package br.ethamorim.discordbot.auroramusic.event.handler

import org.reactivestreams.Publisher

interface InteractionHandler<T> {
    fun handle(mediator: T): Publisher<Any>
}