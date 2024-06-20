package de.smoofy.eurocup.tournament.tabel

import de.smoofy.eurocup.EuroCup
import de.smoofy.eurocup.builder.InventoryBuilder
import de.smoofy.eurocup.builder.ItemBuilder
import de.smoofy.eurocup.fan.Team
import de.smoofy.eurocup.utils.Keys
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.persistence.PersistentDataType


/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 20.06.2024 - 15:30
 */

/**

 */
class TabelInventory {

    fun inventory(): Inventory {
        val inventory = InventoryBuilder(Holder(), 9*6, "<yellow>Tabel")

        inventory.fill(ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).noName())
        var slot = 0
        for (group in Team.Group.entries) {
            if (group == Team.Group.NONE) {
                continue
            }
            inventory.set(Team.Cache.group(group), slot)
            slot += 9
        }

        slot = 0
        val teamSlots = listOf(2, 3, 4, 5)
        var row = 0
        for (tabelTeam in EuroCup.tournamentAPI.tabelTeamsCache) {
            inventory.set(Team.Cache.skull(tabelTeam.team)
                .lore("<gray>Punkte<dark_gray>: <yellow>${tabelTeam.points}",
                    "<gray>Goals<dark_gray>: <yellow>${tabelTeam.goals}",
                    "<gray>Opponent goals<dark_gray>: <yellow>${tabelTeam.opponentGoals}",
                    "<gray>Goal difference<dark_gray>: <yellow>${tabelTeam.goalDifference}",
                    "<gray>Matches<dark_gray>: <yellow>${tabelTeam.matches}",
                    "<gray>Won<dark_gray>: <yellow>${tabelTeam.won}",
                    "<gray>Draw<dark_gray>: <yellow>${tabelTeam.draw}",
                    "<gray>Lost<dark_gray>: <yellow>${tabelTeam.lost}"), teamSlots[slot++] + row * 9)
            if (slot == 4) {
                slot = 0
                row++
            }
        }

        slot = 8
        for (i in 1..6) {
            inventory.set(ItemBuilder(Material.PAPER).name("<green>To the games")
                .data(Keys.MATCH, PersistentDataType.INTEGER, i), slot)

            slot += 9
        }

        return inventory.build()
    }

    class Holder : InventoryHolder {
        override fun getInventory(): Inventory {
            return Bukkit.createInventory(null, 9)
        }
    }
}