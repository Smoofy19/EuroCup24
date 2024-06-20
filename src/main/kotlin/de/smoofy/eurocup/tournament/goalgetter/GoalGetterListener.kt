package de.smoofy.eurocup.tournament.goalgetter

import de.smoofy.eurocup.EuroCup
import de.smoofy.eurocup.utils.Keys
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType


/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 19.06.2024 - 21:45
 */

/**

 */
class GoalGetterListener : Listener {

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
        if (event.clickedInventory!!.holder !is GoalGetterInventory.Holder) {
            return
        }
        event.isCancelled = true

        val player = event.whoClicked as Player

        val item = event.currentItem ?: return
        val page = this.page(item)
        if (page != -1) {
            player.openInventory(EuroCup.INSTANCE.goalGetterInventory.inventory(page))
            return
        }
    }

    private fun page(item: ItemStack): Int {
        val persistentDataContainer = item.itemMeta.persistentDataContainer

        if (persistentDataContainer.has(Keys.NEXT_PAGE.key, PersistentDataType.INTEGER)) {
            return persistentDataContainer.get(Keys.NEXT_PAGE.key, PersistentDataType.INTEGER)!!
        }

        return -1
    }
}