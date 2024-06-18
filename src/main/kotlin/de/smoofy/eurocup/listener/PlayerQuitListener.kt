package de.smoofy.eurocup.listener

import de.smoofy.eurocup.EuroCup
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 24.11.2023 - 17:27
 */

/**

 */

class PlayerQuitListener : Listener {

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        val player = EuroCup.INSTANCE.playerManager.euroCupPlayer(event.player)
        if (player.chair != null) {
            player.chair!!.remove()
            player.chair = null
        }
        event.quitMessage(Component.text("« ", NamedTextColor.GRAY).append(player.displayName()))
        EuroCup.INSTANCE.playerManager.update(player)
    }
}
