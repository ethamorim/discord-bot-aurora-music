package br.ethamorim.discordbot.auroramusic

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class DiscordBotAuroraMusicApplication

fun main(args: Array<String>) {
	runApplication<DiscordBotAuroraMusicApplication>(*args)
}
