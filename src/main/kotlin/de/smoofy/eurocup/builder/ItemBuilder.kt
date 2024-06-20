package de.smoofy.eurocup.builder

import de.smoofy.eurocup.utils.Keys
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.NamespacedKey
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
class ItemBuilder(material: Material) {

    private val itemStack: ItemStack
    private val itemMeta: ItemMeta

    init {
        this.itemStack = ItemStack(material)
        this.itemMeta = itemStack.itemMeta
    }

    fun name(name: String): ItemBuilder {
        this.itemMeta.displayName(MiniMessage.miniMessage().deserialize(name))
        return this
    }

    fun noName(): ItemBuilder {
        return this.name("")
    }

    fun <P, C : Any> data(key: NamespacedKey, type: PersistentDataType<P, C>, value: C): ItemBuilder {
        this.itemMeta.persistentDataContainer.set(key, type, value)
        return this
    }

    fun <P, C : Any> data(key: Keys, type: PersistentDataType<P, C>, value: C): ItemBuilder {
        this.itemMeta.persistentDataContainer.set(key.key, type, value)
        return this
    }

    fun build(): ItemStack {
        this.itemStack.itemMeta = this.itemMeta
        return this.itemStack
    }
}