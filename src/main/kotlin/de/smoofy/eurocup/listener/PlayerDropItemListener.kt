package de.smoofy.eurocup.listener

import de.smoofy.eurocup.EuroCup
import de.smoofy.eurocup.player.Rank
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerDropItemEvent


/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 20.06.2024 - 11:55
 */

/**

 */
class PlayerDropItemListener : Listener {

    @EventHandler
    fun onDrop(event: PlayerDropItemEvent) {
        val player = EuroCup.INSTANCE.playerManager.euroCupPlayer(event.player)
        if (player.hasPriority(Rank.ADMIN.priority) && player.bukkitPlayer().gameMode == GameMode.CREATIVE) {
            return
        }
        event.isCancelled = true
    }
}