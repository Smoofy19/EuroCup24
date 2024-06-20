package de.smoofy.eurocup.listener

import de.smoofy.eurocup.EuroCup
import de.smoofy.eurocup.player.Rank
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent


/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 20.06.2024 - 11:52
 */

/**

 */
class InventoryClickListener : Listener {

    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        if (event.whoClicked !is Player) {
            return
        }
        if (event.clickedInventory == null) {
            return
        }
        val player = EuroCup.INSTANCE.playerManager.euroCupPlayer(event.whoClicked as Player)
        if (event.clickedInventory != player.bukkitPlayer().inventory) {
            return
        }
        if (player.hasPriority(Rank.ADMIN.priority) && player.bukkitPlayer().gameMode == GameMode.CREATIVE) {
            return
        }
        event.isCancelled = true
    }
}