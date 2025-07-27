package br.ethamorim.discordbot.auroramusic.event.listener

import br.ethamorim.discordbot.auroramusic.event.handler.WeeklyAlbumSenderInteractionHandler
import br.ethamorim.discordbot.auroramusic.property.ChannelProperties
import discord4j.core.GatewayDiscordClient
import discord4j.core.`object`.entity.channel.TextChannel
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class WeeklyAlbumSenderScheduledListener(
    private val client: GatewayDiscordClient,
    private val channelProperties: ChannelProperties,
    private val handler: WeeklyAlbumSenderInteractionHandler
) : ScheduledListener {

//    @Scheduled(cron = "0 0 9 ? * SUN", zone = "America/Sao_Paulo")
    @Scheduled(cron = "*/30 * * * * *", zone = "America/Sao_Paulo")
    override fun listen() {
        client.getChannelById(channelProperties.channelTextBot)
            .ofType(TextChannel::class.java)
            .flatMap { it -> handler.handle(it) }
            .subscribe()
    }
}