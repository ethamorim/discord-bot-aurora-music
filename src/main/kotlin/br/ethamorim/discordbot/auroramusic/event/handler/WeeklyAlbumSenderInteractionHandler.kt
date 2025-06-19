package br.ethamorim.discordbot.auroramusic.event.handler

import discord4j.core.`object`.entity.channel.TextChannel
import org.reactivestreams.Publisher
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import kotlin.inc

@Component
class WeeklyAlbumSenderInteractionHandler(
    @Value("\${discordbot.user-list}") private val members: List<String>
) : TextInteractionHandler {

    private final var order = -1

    override fun handle(mediator: TextChannel): Publisher<Any> {
        order++
        if (order == members.size)
            order = 0
        println()
        return Mono.fromRunnable {  }
//        return mediator.createMessage("This is a test! Time for your mom! to send the album")
    }
}