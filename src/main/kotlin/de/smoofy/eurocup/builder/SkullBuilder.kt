package de.smoofy.eurocup.builder

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.persistence.PersistentDataType
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
    private val meta: ItemMeta

    init {
        this.item = ItemStack(Material.PLAYER_HEAD)
        this.meta = item.itemMeta as SkullMeta

        val method = meta.javaClass.getDeclaredMethod("setProfile", GameProfile::class.java)
        method.isAccessible = true
        method.invoke(meta, this.profile(texture))
    }

    fun name(name: Component): SkullBuilder {
        meta.displayName(name)
        return this
    }

    fun lore(lore: Component): SkullBuilder {
        this.meta.lore(listOf(lore))
        return this
    }

    fun data(key: NamespacedKey, type: PersistentDataType<String, String>, value: String): SkullBuilder {
        this.meta.persistentDataContainer.set(key, type, value)
        return this
    }

    fun build(): ItemStack {
        this.item.setItemMeta(meta)
        return this.item
    }

    private fun profile(base64: String): GameProfile {
        val uuid = UUID(base64.substring(base64.length - 20).hashCode().toLong(), base64.substring(base64.length - 10).hashCode().toLong())
        val profile = GameProfile(uuid, "")
        profile.properties.put("textures", Property("textures", base64))
        return profile
    }
}