package br.ethamorim.discordbot.auroramusic.config

import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.Event
import discord4j.rest.RestClient
import discord4j.rest.service.ApplicationService
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@TestConfiguration
class BotConfig {
    @Bean
    @Primary
    fun getGatewayDiscordClient(): GatewayDiscordClient? {
        val gatewayDiscordClient = mock(GatewayDiscordClient::class.java)
        val restClient = mock(RestClient::class.java)
        val applicationService = mock(ApplicationService::class.java)
        val monoLong = mock(Mono::class.java) as Mono<Long>

        whenever(gatewayDiscordClient.on<Event, Void>(any(), any()))
            .thenReturn(Flux.empty())
        whenever(gatewayDiscordClient.restClient)
            .thenReturn(restClient)
        whenever(restClient.applicationId)
            .thenReturn(monoLong)
        whenever(monoLong.block())
            .thenReturn(0L)
        whenever(restClient.applicationService)
            .thenReturn(applicationService)
        whenever(applicationService.bulkOverwriteGuildApplicationCommand(any(), any(), any()))
            .thenReturn(Flux.empty())

        return gatewayDiscordClient
    }
}