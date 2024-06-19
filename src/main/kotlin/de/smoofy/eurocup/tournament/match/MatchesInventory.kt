package de.smoofy.eurocup.tournament.match

import de.smoofy.eurocup.EuroCup
import de.smoofy.eurocup.builder.InventoryBuilder
import de.smoofy.eurocup.builder.ItemBuilder
import de.smoofy.eurocup.builder.SkullBuilder
import de.smoofy.eurocup.fan.Team
import de.smoofy.eurocup.utils.Keys
import de.smoofy.eurocup.utils.Skulls
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
 * Created - 18.06.2024 - 12:49
 */

/**

 */
class MatchesInventory(private val id: Int) {

    fun groupInventory(): Inventory {
        val inventory = InventoryBuilder(Holder(), 9*5, "<yellow>All matches")
        val group = this.group(id)

        inventory.fill(ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).noName())
        inventory.set(Team.Cache.group(group)
            .name("<yellow>Group ${group.name}"), 4)

        this.setMatches(inventory, group)

        if (id != 1) {
            inventory.set(ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                    .name("<red>Previous page")
                    .data(Keys.PREVIOUS_PAGE, PersistentDataType.INTEGER, id - 1), 36
            )
        }

        if (id != 6) {
            inventory.set(ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                    .name("<green>Next page")
                    .data(Keys.NEXT_PAGE, PersistentDataType.INTEGER, id + 1), 44
            )
        }

        return inventory.build()
    }

    private fun setMatches(inventory: InventoryBuilder, group: Team.Group) {
        val slots = listOf(9, 14, 18, 23, 27, 32)
        var currentSlot = 0
        EuroCup.tournamentAPI.matchesCache.filter { match -> match.teamOne.group == group }.forEach { match ->
            var slot = slots[currentSlot++]
            inventory.set(this.matchDayItem(match), slot++)
            inventory.set(Team.Cache.skull(match.teamOne)
                .name("${match.teamOne.gradient}${match.teamOne.name}</gradient>"), slot++)
            inventory.set(Team.Cache.skull(match.teamTwo)
                .name("${match.teamTwo.gradient}${match.teamTwo.name}</gradient>"), slot++)
            inventory.set(ItemBuilder(Material.PAPER).name("<green>To the game")
                .data(Keys.MATCH, PersistentDataType.INTEGER, match.matchId), slot)
        }
    }

    private fun matchDayItem(match: Match): ItemBuilder {
        val item = ItemBuilder(SkullBuilder(Skulls.FOOTBALL.texture).build())
            .name("<green>${this.matchDay(match.phase)}")
            .lore("<gold>${match.time}")

        return item
    }

    private fun matchDay(name: String): String {
        return if (name.startsWith("1. Runde")) {
            "Matchday 1"
        } else if (name.startsWith("2. Runde")) {
            "Matchday 2"
        } else if (name.startsWith("3. Runde")) {
            "Matchday 3"
        } else if (name.startsWith("Achtelfinale")) {
            "Round of sixteen"
        } else if (name.startsWith("Viertelfinale")) {
            "Quarterfinals"
        } else if (name.startsWith("Halbfinale")) {
            "Semifinal"
        } else {
            "Final"
        }
    }

    private fun group(id: Int): Team.Group {
        return when (id) {
            1 -> Team.Group.A
            2 -> Team.Group.B
            3 -> Team.Group.C
            4 -> Team.Group.D
            5 -> Team.Group.E
            6 -> Team.Group.F
            else -> Team.Group.A
        }
    }

    class Holder : InventoryHolder {
        override fun getInventory(): Inventory {
            return Bukkit.createInventory(null, 9)
        }
    }
}