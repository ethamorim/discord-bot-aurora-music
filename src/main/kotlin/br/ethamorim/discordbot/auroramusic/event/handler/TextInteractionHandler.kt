package br.ethamorim.discordbot.auroramusic.event.handler

import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.channel.TextChannel

interface TextInteractionHandler : InteractionHandler<TextChannel, Message>