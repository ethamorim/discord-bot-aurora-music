package br.ethamorim.discordbot.auroramusic.event.handler

import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.channel.TextChannel
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class WeeklyAlbumSenderInteractionHandler(
    @Value("\${discordbot.user-list}") private val members: List<String>
) : TextInteractionHandler {

    private final var order = -1
    private final var messages = listOf("Time for %s to send the album!");

    override fun handle(mediator: TextChannel): Mono<Message> {
        order++
        if (order == members.size)
            order = 0
        return mediator.createMessage(String.format(messages.get(0), members[order]))
    }
}