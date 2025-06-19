package br.ethamorim.discordbot.auroramusic.config

import br.ethamorim.discordbot.auroramusic.event.command.CommandRequestDTO
import com.fasterxml.jackson.databind.ObjectMapper
import discord4j.core.GatewayDiscordClient
import discord4j.discordjson.json.ApplicationCommandOptionData
import discord4j.discordjson.json.ApplicationCommandRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.stereotype.Component

@Component
class CommandConfig(
    @Value("\${discordbot.guild-id}") val guildId: Long,
    val gatewayClient: GatewayDiscordClient,
    val objectMapper: ObjectMapper
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val resolver = PathMatchingResourcePatternResolver()
        val resources = resolver.getResources("classpath:commands/*")

        val commandsRequests = resources.map { resource ->
            val json = resource.inputStream.bufferedReader().use { it.readText() }
            val commandRequest = objectMapper.readValue(json, CommandRequestDTO::class.java)

            val applicationCommandRequestBuilder = ApplicationCommandRequest.builder()
                .name(commandRequest.name)
                .description(commandRequest.description)

            commandRequest.options.forEach {
                applicationCommandRequestBuilder.addOption(ApplicationCommandOptionData.builder()
                    .name(it.name)
                    .description(it.description)
                    .type(it.type)
                    .required(it.required)
                    .build())
            }

            applicationCommandRequestBuilder.build()
        }

        val applicationId = gatewayClient.restClient.applicationId.block()!!
        gatewayClient.restClient.applicationService
            .bulkOverwriteGuildApplicationCommand(applicationId, guildId, commandsRequests)
            .doOnNext { println("Succesfully registered Global Command ${it.name()}") }
            .doOnError { println("Failed to register guild command $it") }
            .subscribe()
    }
}