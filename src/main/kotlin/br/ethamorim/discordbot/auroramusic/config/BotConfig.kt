package br.ethamorim.discordbot.auroramusic.config

import discord4j.core.DiscordClientBuilder
import discord4j.core.GatewayDiscordClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BotConfig(@Value("\${discordbot.auth-token}") private val token: String) {
    @Bean
    fun getGatewayDiscordClient(): GatewayDiscordClient? {
        return DiscordClientBuilder.create(token).build()
            .gateway()
            .login()
            .block()
    }
}