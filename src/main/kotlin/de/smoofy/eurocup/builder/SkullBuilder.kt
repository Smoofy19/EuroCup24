package de.smoofy.eurocup.builder

import de.smoofy.eurocup.utils.Keys
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.persistence.PersistentDataType
import java.net.URL
import java.util.*

/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 17.06.2024 - 20:18
 */

/**

 */
class SkullBuilder(texture: String) {

    private val item: ItemStack
    private val meta: SkullMeta

    init {
        this.item = ItemStack(Material.PLAYER_HEAD)
        this.meta = item.itemMeta as SkullMeta

        val profile = Bukkit.createProfile(UUID.randomUUID())
        val playerTexture = profile.textures
        playerTexture.skin = URL("https://textures.minecraft.net/texture/$texture")
        profile.setTextures(playerTexture)
        this.meta.playerProfile = profile
    }

    fun name(name: String): SkullBuilder {
        this.meta.displayName(MiniMessage.miniMessage().deserialize(name))
        return this
    }

    fun clearLore() {
        this.meta.lore(null)
    }

    fun lore(line: String): SkullBuilder {
        var lore: MutableList<Component>? = mutableListOf()
        if (this.meta.hasLore()) {
            lore = this.meta.lore()
        }
        if (lore == null) {
            return this
        }
        lore.add(MiniMessage.miniMessage().deserialize(line))
        this.meta.lore(lore)
        return this
    }

    fun lore(vararg lore: String): SkullBuilder {
        lore.forEach { this.lore(it) }
        return this
    }

    fun <P, C : Any> data(key: NamespacedKey, type: PersistentDataType<P, C>, value: C): SkullBuilder {
        this.meta.persistentDataContainer.set(key, type, value)
        return this
    }

    fun <P, C : Any> data(key: Keys, type: PersistentDataType<P, C>, value: C): SkullBuilder {
        this.meta.persistentDataContainer.set(key.key, type, value)
        return this
    }

    fun build(): ItemStack {
        this.item.itemMeta = this.meta
        return this.item
    }
}