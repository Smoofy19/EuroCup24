package de.smoofy.eurocup.fan

import de.smoofy.eurocup.EuroCup
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory


/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 17.06.2024 - 22:21
 */

/**

 */
class FanListener(private val fanManager: FanManager) : Listener {

    init {
        Bukkit.getPluginManager().registerEvents(this, EuroCup.INSTANCE)
    }

    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        if (event.whoClicked !is Player) {
            return
        }
        if (event.clickedInventory == null) {
            return
        }
        val inventory: Inventory = event.clickedInventory!!
        if (inventory.holder == null) {
            return
        }
        if (inventory.holder !is FanInventory.Holder) {
            return
        }

        val item = event.currentItem ?: return
        val player = EuroCup.INSTANCE.playerManager.euroCupPlayer(event.whoClicked as Player)
        val country = fanManager.country(item)
        if (country != null) {
            player.team = country
            EuroCup.INSTANCE.tablist.set(player)
        }
        player.bukkitPlayer().closeInventory()
    }
}