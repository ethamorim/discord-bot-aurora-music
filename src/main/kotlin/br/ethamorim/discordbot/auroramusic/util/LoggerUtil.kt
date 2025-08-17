package br.ethamorim.discordbot.auroramusic.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
object LoggerUtil {
    fun getLogger(anyClass: Class<*>): Logger {
        return LoggerFactory.getLogger(anyClass)
    }
}