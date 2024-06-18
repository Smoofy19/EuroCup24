package de.smoofy.eurocup.fan

import de.smoofy.eurocup.EuroCup
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType


/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 17.06.2024 - 22:25
 */

/**

 */
class FanManager {

    val key: NamespacedKey = NamespacedKey(EuroCup.INSTANCE, "country")

    val fanInventory = FanInventory(this)

    init {
        FanListener(this)
    }

    fun country(item: ItemStack): Team? {
        if (item.itemMeta == null) {
            return null
        }
        if (!item.itemMeta.persistentDataContainer.has(this.key, PersistentDataType.STRING)) {
            return null
        }
        val name = item.itemMeta.persistentDataContainer.get(this.key, PersistentDataType.STRING) ?: return null

        return Team.country(name)
    }
}