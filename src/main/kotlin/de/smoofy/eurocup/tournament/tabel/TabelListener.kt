package de.smoofy.eurocup.tournament.tabel

import de.smoofy.eurocup.tournament.match.MatchesInventory
import de.smoofy.eurocup.utils.Keys
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.persistence.PersistentDataType


/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 20.06.2024 - 15:30
 */

/**

 */
class TabelListener : Listener {

    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        if (event.whoClicked !is Player) {
            return
        }
        if (event.clickedInventory == null) {
            return
        }
        if (event.clickedInventory!!.holder == null) {
            return
        }
        if (event.clickedInventory!!.holder !is TabelInventory.Holder) {
            return
        }
        event.isCancelled = true

        val player = event.whoClicked as Player

        val item = event.currentItem ?: return

        val persistentDataContainer = item.itemMeta.persistentDataContainer
        if (persistentDataContainer.has(Keys.MATCH.key)) {
            val id = persistentDataContainer.get(Keys.MATCH.key, PersistentDataType.INTEGER)!!
            player.openInventory(MatchesInventory(id).groupInventory())
            return
        }
    }
}