package de.smoofy.eurocup.fan

import de.smoofy.eurocup.EuroCup
import de.smoofy.eurocup.builder.InventoryBuilder
import de.smoofy.eurocup.player.EuroCupPlayer
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType


/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 17.06.2024 - 21:56
 */

/**

 */
class FanInventory(fanManager: FanManager) {

    private val inventory = InventoryBuilder(Holder(), 9*6, "<red>Choose your favorite team!").build()

    init {
        var slot = 0
        Team.Group.entries.forEach { group ->
            if (group != Team.Group.NONE) {
                inventory.setItem(slot++, Team.Cache.group(group)
                    .name("<yellow>Group ${group.name}").build())
            }
        }

        slot = 18
        var column = 0
        for (team in Team.entries) {
            if (team == Team.NONE) {
                slot = 8
            }
            inventory.setItem(slot, Team.Cache.skull(team)
                .name(team.gradient + team.name)
                .data(fanManager.key, PersistentDataType.STRING, team.name)
                .build())

            slot += 9
            if (slot > 53) {
                column++
                slot = 18 + column
            }
        }

        for (i in 0..<inventory.size) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, this.placeHolder())
            }
        }
    }

    fun inventory(player: EuroCupPlayer): Inventory {
        val inventory = Bukkit.createInventory(Holder(), 9*6, EuroCup.miniMessage.deserialize("<red>Choose your favorite team!"))
        inventory.contents = this.inventory.contents.clone()
        inventory.setItem(53, Team.Cache.skull(player.team)
            .name(player.team.gradient + player.team.name)
            .lore("<green>Your current favorite team", true).build())

        return inventory
    }

    private fun placeHolder(): ItemStack {
        val item = ItemStack(Material.GRAY_STAINED_GLASS_PANE)
        val meta = item.itemMeta
        meta.displayName(Component.text(" "))
        item.itemMeta = meta
        return item
    }

    class Holder : InventoryHolder {
        override fun getInventory(): Inventory {
            return Bukkit.createInventory(null, 9)
        }
    }
}