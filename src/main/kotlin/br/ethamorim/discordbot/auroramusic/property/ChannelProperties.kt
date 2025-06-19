package br.ethamorim.discordbot.auroramusic.property

import discord4j.common.util.Snowflake
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "discordbot.channel.id")
data class ChannelProperties(
    private val textBot: String
) {
    val channelTextBot = Snowflake.of(textBot)
}