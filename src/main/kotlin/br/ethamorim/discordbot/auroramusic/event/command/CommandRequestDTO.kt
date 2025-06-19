package br.ethamorim.discordbot.auroramusic.event.command

data class CommandRequestDTO(val name: String, val description: String, val options: List<CommandOptionsDTO>)