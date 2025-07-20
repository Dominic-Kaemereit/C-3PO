package dev.dominic.c2po

import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import dev.dominic.c2po.handler.AIHandler
import dev.dominic.c2po.listener.MessageReceivedListener
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class Bot {

    private val apiKey: String = System.getenv("DISCORD_API_KEY")
        ?: throw IllegalStateException("DISCORD_API_KEY environment variable not set")

    val aIHandler = AIHandler()

    val jda = JDABuilder.createDefault(
        apiKey,
        GatewayIntent.GUILD_MESSAGES,
        GatewayIntent.MESSAGE_CONTENT,
        GatewayIntent.GUILD_MEMBERS
    ).build()

    init {
        jda.addEventListener(MessageReceivedListener(this))

        setC2POActivityChanger()
    }

    private fun setC2POActivityChanger() {
        val activities = listOf(
            Activity.watching("die galaktische Etikette"),
            Activity.playing("über sechs Millionen Kommunikationsformen"),
            Activity.listening("wie üblich große Besorgnis"),
            Activity.competing("Sir Anthony Daniels"),
            Activity.playing("die Erfolgschancen... sie sind ziemlich gering")
        )

        val scheduler = Executors.newSingleThreadScheduledExecutor()
        var currentIndex = 0

        scheduler.scheduleAtFixedRate({
            jda.presence.activity = activities[currentIndex]

            currentIndex = (currentIndex + 1) % activities.size
        }, 0, 30, TimeUnit.SECONDS)
    }

}

fun main(args: Array<String>) {
    Bot()
}