package de.smoofy.eurocup.listener

import de.smoofy.eurocup.EuroCup
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerKickEvent

/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 24.11.2023 - 17:27
 */

/**

 */

class PlayerJoinListener : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val playerManager = EuroCup.INSTANCE.playerManager
        val player = playerManager.euroCupPlayer(event.player)
        player.bukkitPlayer().teleport(Location(player.bukkitPlayer().world, 0.5, 100.5, 0.5, 0f, 0f))
        event.joinMessage(Component.text("» ", NamedTextColor.GRAY).append(player.displayName()))
        playerManager.cacheEuroCupPlayer(player)
        EuroCup.INSTANCE.tablist.set(player)

        Bukkit.getScheduler().runTaskLater(EuroCup.INSTANCE, { ->
            if (!player.labyMod) player.bukkitPlayer().kick(Component.text("You have to use LabyMod to play on this server!", NamedTextColor.RED), PlayerKickEvent.Cause.PLUGIN)
        }, 20L)
    }
}
