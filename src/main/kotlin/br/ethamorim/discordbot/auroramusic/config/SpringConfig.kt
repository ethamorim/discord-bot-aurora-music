package br.ethamorim.discordbot.auroramusic.config

import br.ethamorim.discordbot.auroramusic.property.ChannelProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
@EnableScheduling
@EnableConfigurationProperties(ChannelProperties::class)
class SpringConfig