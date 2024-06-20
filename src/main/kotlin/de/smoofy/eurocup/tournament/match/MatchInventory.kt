package de.smoofy.eurocup.tournament.match

import de.smoofy.eurocup.builder.InventoryBuilder
import de.smoofy.eurocup.builder.ItemBuilder
import de.smoofy.eurocup.builder.SkullBuilder
import de.smoofy.eurocup.fan.Team
import de.smoofy.eurocup.utils.Skulls
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder


/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 19.06.2024 - 22:04
 */

/**

 */
class MatchInventory(private val match: Match) {

    private val football = SkullBuilder(Skulls.FOOTBALL.texture)
    private val info = SkullBuilder(Skulls.INFO.texture)
    private val city = SkullBuilder(Skulls.CITY.texture)
    private val result = SkullBuilder(Skulls.RESULT.texture)

    fun inventory(): Inventory {
        val inventory = InventoryBuilder(Holder(), this.calculateSize()*9, "")

        inventory.fill(ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).noName())

        inventory.set(Team.Cache.skull(match.teamOne), 1)
        inventory.set(Team.Cache.skull(match.teamTwo), 7)

        val slots = listOf(10, 16)
        val items = listOf(
            ItemBuilder(Material.LIME_STAINED_GLASS_PANE).name("<green>Winner!"),
            ItemBuilder(Material.RED_STAINED_GLASS_PANE).name("<red>Looser!")
        )
        val draw = ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).name("<gold>Draw!")
        try {
            if (this.winner() == match.teamOne) {
                for ((slot, item) in items.withIndex()) {
                    inventory.set(item, slots[slot])
                }
            } else if (this.winner() == match.teamTwo) {
                for ((slot, item) in items.reversed().withIndex()) {
                    inventory.set(item, slots[slot])
                }
            } else {
                for (slot in slots) {
                    inventory.set(draw, slot)
                }
            }
        }catch (ignored: IndexOutOfBoundsException) {}

        inventory.set(info
            .name("<yellow>Match Info")
            .lore("<gray>Kickoff<dark_gray>: <yellow>${match.time}",
                "<gray>Matchday<dark_gray>: <yellow>${this.matchDay(match.phase)}"), 3)
        inventory.set(result
            .name("<yellow>Result")
            .lore("<gray>Half time<dark_gray>: <yellow>${match.matchResult.halfTimeResult}",
                "<gray>End<dark_gray>: <yellow>${match.matchResult.endResult}"), 4)
        inventory.set(city
            .name("<yellow>Location info")
            .lore("<gray>City<dark_gray>: <yellow>${match.location.city}",
                "<gray>Stadium<dark_gray>: <yellow>${match.location.stadium}",
                "<gray>Viewers<dark_gray>: <yellow>${match.location.viewers}"), 5)

        var slot = 27
        for (goal in this.goals()) {
            football.clearLore()
            inventory.set(football
                .name(goal.scorer)
                .lore("<gray>Match minute<dark_gray>: <yellow>${goal.minute}",
                    "<gray>Score<dark_gray>: ${match.teamOne.gradient}${goal.scoreTeam1}</gradient><dark_gray>:" +
                            "${match.teamTwo.gradient}${goal.scoreTeam2}</gradient>",
                    "<gray>Penalty<dark_gray>: <yellow>${goal.penalty}",
                    "<gray>Own goal<dark_gray>: <yellow>${goal.ownGoal}",
                    "<gray>Overtime<dark_gray>: <yellow>${goal.overtime}"), slot++)
        }

        return inventory.build()
    }

    private fun calculateSize(): Int {
        if (this.goals().size <= 9) {
            return 4
        }
        if (this.goals().size > 9) {
            return 5
        }
        return 6
    }

    private fun matchDay(name: String): String {
        return if (name.startsWith("1. Runde")) {
            "1"
        } else if (name.startsWith("2. Runde")) {
            "2"
        } else if (name.startsWith("3. Runde")) {
            "3"
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

    private fun winner(): Team {
        val result = match.matchResult.endResult.split(":")
        if (result[0] > result[1]) {
            return match.teamOne
        }
        if (result[1] > result[0]) {
            return match.teamTwo
        }
        return Team.NONE
    }

    private fun goals(): List<Match.Goal> {
        return this.match.goals.sortedBy { goal -> goal.minute }
    }

    class Holder : InventoryHolder {
        override fun getInventory(): Inventory {
            return Bukkit.createInventory(null, 9)
        }
    }
}