package de.smoofy.eurocup.listener

import de.smoofy.eurocup.EuroCup
import de.smoofy.eurocup.player.Rank
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent

/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 24.11.2023 - 17:27
 */

/**

 */

class BlockPlaceListener : Listener {

    @EventHandler
    fun onPlace(event: BlockPlaceEvent) {
        val player = EuroCup.INSTANCE.playerManager.euroCupPlayer(event.player)
        if (player.hasPriority(Rank.ADMIN.priority) && player.bukkitPlayer().gameMode == GameMode.CREATIVE) {
            return
        }
        event.isCancelled = true
    }

}
