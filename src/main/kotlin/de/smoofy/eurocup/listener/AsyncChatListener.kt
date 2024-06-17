package de.smoofy.eurocup.listener

import de.smoofy.eurocup.EuroCup
import de.smoofy.eurocup.player.Rank
import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 24.11.2023 - 17:27
 */

/**

 */

class AsyncChatListener : Listener {

    @EventHandler
    fun onChat(event: AsyncChatEvent) {
        val player = EuroCup.INSTANCE.playerManager.euroCupPlayer(event.player)
        if (player.muted) {
            event.isCancelled = true
            player.bukkitPlayer().sendMessage(Component.text("You have been banned from the chat!", NamedTextColor.RED))
            return
        }
        event.renderer { _, sourceDisplayName, message, _ ->
            val messageContent = PlainTextComponentSerializer.plainText().serialize(message)
            if (player.hasPriority(Rank.ADMIN.priority)) {
                Component.text().append(sourceDisplayName.color(player.rank.color), Component.text(": ", NamedTextColor.GRAY),
                    EuroCup.miniMessage.deserialize(messageContent)).build()
            } else {
                Component.text().append(sourceDisplayName.color(player.rank.color), Component.text(": ", NamedTextColor.GRAY),
                    message).build()
            }
        }
    }
}
