package de.smoofy.eurocup.fan

import de.smoofy.eurocup.EuroCup
import de.smoofy.eurocup.builder.ItemBuilder
import de.smoofy.eurocup.player.EuroCupPlayer
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
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

    private val inventory = Bukkit.createInventory(Holder(), 9*6, EuroCup.miniMessage.deserialize("<red>Choose your favorite team!"))

    init {
        var slot = 0
        Team.Group.entries.forEach { group ->
            if (group != Team.Group.NONE) {
                inventory.setItem(slot++, ItemBuilder(Team.Cache.group(group))
                    .name(Component.text(group.name, NamedTextColor.RED)).build())
            }
        }

        slot = 18
        var column = 0
        for (team in Team.entries) {
            if (team == Team.NONE) slot = 8
            inventory.setItem(slot, ItemBuilder(Team.Cache.skull(team))
                .name(Component.text(team.name, NamedTextColor.RED))
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
        inventory.setItem(53, ItemBuilder(Team.Cache.skull(player.team))
            .name(Component.text(player.team.name, NamedTextColor.RED))
            .lore(Component.text("Your current favorite team", NamedTextColor.GREEN)).build())

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