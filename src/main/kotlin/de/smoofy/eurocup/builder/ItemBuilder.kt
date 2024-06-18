package de.smoofy.eurocup.builder

import de.smoofy.eurocup.utils.Keys
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType


/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 18.06.2024 - 19:09
 */

/**

 */
class ItemBuilder(val material: Material) {

    private val itemStack: ItemStack
    private val itemMeta: ItemMeta

    init {
        this.itemStack = ItemStack(material)
        this.itemMeta = itemStack.itemMeta
    }

    fun name(name: Component): ItemBuilder {
        this.itemMeta.displayName(name)
        return this
    }

    fun lore(line: Component): ItemBuilder {
        var lore: MutableList<Component>? = mutableListOf()
        if (this.itemMeta.hasLore()) {
            lore = this.itemMeta.lore()
        }
        if (lore == null) {
            return this
        }
        lore.add(line)
        this.itemMeta.lore(lore)
        return this
    }

    fun lore(vararg lore: Component): ItemBuilder {
        lore.forEach { this.lore(it) }
        return this
    }

    fun data(key: Keys, type: PersistentDataType<String, String>, value: String): ItemBuilder {
        this.itemMeta.persistentDataContainer.set(key.key, type, value)
        return this
    }

    fun build(): ItemStack {
        this.itemStack.itemMeta = this.itemMeta
        return this.itemStack
    }
}