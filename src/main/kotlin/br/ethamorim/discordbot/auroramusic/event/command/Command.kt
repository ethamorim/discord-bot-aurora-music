package br.ethamorim.discordbot.auroramusic.event.command

enum class Command(val commandName: String) {
    START_ALBUM_CYCLE("start-album-cycle");

    fun equalsCommandName(commandName: String): Boolean {
        return commandName == this.commandName
    }
}