package de.smoofy.eurocup.builder

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder


/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 19.06.2024 - 18:06
 */

/**

 */
class InventoryBuilder(holder: InventoryHolder?, size: Int, title: String) {

    val inventory: Inventory

    init {
        this.inventory = Bukkit.createInventory(holder, size, MiniMessage.miniMessage().deserialize(title))
    }

    fun fill(item: ItemBuilder): InventoryBuilder {
        for (i in 0 until inventory.size) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, item.build())
            }
        }
        return this
    }

    fun set(builder: ItemBuilder, vararg slots: Int): InventoryBuilder {
        for (slot in slots) {
            inventory.setItem(slot, builder.build())
        }
        return this
    }

    fun set(builder: SkullBuilder, vararg slots: Int): InventoryBuilder {
        for (slot in slots) {
            inventory.setItem(slot, builder.build())
        }
        return this
    }

    fun build(): Inventory {
        return this.inventory
    }
}