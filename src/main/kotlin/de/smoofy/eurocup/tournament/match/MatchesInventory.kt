package de.smoofy.eurocup.tournament.match

import de.smoofy.eurocup.EuroCup
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder


/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 18.06.2024 - 12:49
 */

/**

 */
class MatchesInventory {

    private val inventory = Bukkit.createInventory(Holder(), 9*6, EuroCup.miniMessage.deserialize(""))

    init {

    }


    class Holder : InventoryHolder {
        override fun getInventory(): Inventory {
            return Bukkit.createInventory(null, 9)
        }
    }
}