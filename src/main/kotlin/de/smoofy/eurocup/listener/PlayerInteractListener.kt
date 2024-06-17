package de.smoofy.eurocup.listener

import de.smoofy.eurocup.EuroCup
import de.smoofy.eurocup.player.Rank
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 24.11.2023 - 17:27
 */

/**

 */

class PlayerInteractListener : Listener {

    @EventHandler
    fun onAct(event: PlayerInteractEvent) {
        val player = EuroCup.INSTANCE.playerManager.euroCupPlayer(event.player)
        if (!player.hasPriority(Rank.ADMIN.priority) || player.bukkitPlayer().gameMode != GameMode.CREATIVE) {
            event.isCancelled = true
            return
        }
        if (event.action != Action.RIGHT_CLICK_BLOCK || event.clickedBlock == null) {
            return
        }

    }
}
