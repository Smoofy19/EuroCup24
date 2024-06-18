package de.smoofy.eurocup.listener

import de.smoofy.eurocup.EuroCup
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDismountEvent


/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 18.06.2024 - 18:56
 */

/**

 */
class EntityDismountListener : Listener {

    @EventHandler
    fun onDismount(event: EntityDismountEvent) {
        if (event.entity !is Player) {
            return
        }
        val player = EuroCup.INSTANCE.playerManager.euroCupPlayer(event.entity as Player)
        if (player.chair == null) {
            return
        }
        player.chair!!.remove()
        player.chair = null
        player.bukkitPlayer().teleport(player.bukkitPlayer().location.clone().add(0.0, 1.0, 0.0))
    }
}