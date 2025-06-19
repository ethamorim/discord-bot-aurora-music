package br.ethamorim.discordbot.auroramusic.event.handler

import reactor.core.publisher.Mono

interface InteractionHandler<M, O> {
    fun handle(mediator: M): Mono<O>
}