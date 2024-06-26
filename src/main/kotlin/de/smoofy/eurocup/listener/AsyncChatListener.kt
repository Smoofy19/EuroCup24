package de.smoofy.eurocup.listener

import de.smoofy.eurocup.EuroCup
import de.smoofy.eurocup.fan.Team
import de.smoofy.eurocup.player.Rank
import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextReplacementConfig
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.entity.Player
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
        var prefix: Component = Component.empty()
        var suffix: Component = Component.empty()
        var message = event.message()
        if (player.hasPriority(Rank.ADMIN.priority)) {
            message = MiniMessage.miniMessage().deserialize(MiniMessage.miniMessage().serialize(message).replace("\\", ""))
        }
        if (PlainTextComponentSerializer.plainText().serialize(message).startsWith("@fans ") && player.team != Team.NONE) {
            prefix = MiniMessage.miniMessage().deserialize("<gray>[<red>FanChat<gray>] ")
            message = message.replaceText(TextReplacementConfig.builder().match("@fans ").once().replacement("").build())

            event.viewers().removeIf { audience -> audience is Player &&
                    EuroCup.INSTANCE.playerManager.euroCupPlayer(audience).team != player.team &&
                    !EuroCup.INSTANCE.playerManager.euroCupPlayer(audience).hasPriority(Rank.ADMIN.priority)
            }
        }
        if (player.team != Team.NONE) {
            suffix = MiniMessage.miniMessage().deserialize(" <gray>[${player.team.gradient}${player.team.countryCode}</gradient><gray>]")
        }
        event.renderer { _, _, _, _ ->
            Component.text().append(prefix, player.displayName(), suffix, Component.text(": ", NamedTextColor.GRAY), message).build()
        }
    }
}
