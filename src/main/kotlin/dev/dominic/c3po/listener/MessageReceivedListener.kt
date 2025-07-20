package dev.dominic.c2po.listener

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import dev.dominic.c2po.Bot


class MessageReceivedListener(
    private val bot: Bot
): ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.author.isBot) return

        val rawMessage = event.message.contentRaw
        val botMention = event.jda.selfUser.asMention

        if (!rawMessage.contains(botMention))
            return


        val command = rawMessage.removePrefix(botMention).trim().lowercase()

        event.channel.sendTyping().queue()

        bot.aIHandler.sendContent(command).thenApply {
            event.message.reply("""
$it

-# C-2PO basiert auf einem KI-Modell zur Verarbeitung von Fragen, daher sind Missverständnisse oder Fehler nicht ausgeschlossen.
-# C-2PO is based on an AI model for processing questions; therefore, misunderstandings or errors cannot be ruled out.
            """.trimIndent()).queue()
        }.exceptionally {

            event.message.reply("Oh, mein lieber!\n" +
                    "Ein unvorhergesehener Fehler ist aufgetreten!\n" +
                    "Das war ganz bestimmt nicht vorgesehen!\n" +
                    "Meine Schaltkreise sind in heller Aufregung!\n" +
                    "Vielleicht ein Kurzschluss... oder Sabotage?\n" +
                    "Ich fürchte, wir sind in ernsthaften Schwierigkeiten!\n" +
                    "Oh, warum passiert mir das nur ständig?\n" +
                    "Bitte sagen Sie mir, dass R2 daran nicht beteiligt war!").queue()
        }
    }
}