package de.smoofy.eurocup.listener

import de.smoofy.eurocup.EuroCup
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerLoginEvent

/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 24.11.2023 - 17:27
 */

/**

 */

class PlayerLoginListener : Listener {

    @EventHandler
    fun onLogin(event: PlayerLoginEvent) {
        val playerManager = EuroCup.INSTANCE.playerManager
        playerManager.init(event.player.uniqueId, banned = false, muted = false)

        val player = playerManager.euroCupPlayer(event.player)
        if (!player.banned) {
            return
        }

        event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("You have been banned from the server!", NamedTextColor.RED))
    }
}
