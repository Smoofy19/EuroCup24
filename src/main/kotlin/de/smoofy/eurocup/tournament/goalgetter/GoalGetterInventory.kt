package de.smoofy.eurocup.tournament.goalgetter

import de.smoofy.eurocup.EuroCup
import de.smoofy.eurocup.builder.InventoryBuilder
import de.smoofy.eurocup.builder.ItemBuilder
import de.smoofy.eurocup.builder.SkullBuilder
import de.smoofy.eurocup.utils.Keys
import de.smoofy.eurocup.utils.Skulls
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.persistence.PersistentDataType
import kotlin.math.ceil
import kotlin.math.min


/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 20.06.2024 - 12:04
 */

/**

 */
class GoalGetterInventory {

    private val gold = SkullBuilder(Skulls.GOLD_TROPHY.texture)
    private val silver = SkullBuilder(Skulls.SILVER_TROPHY.texture)
    private val bronze = SkullBuilder(Skulls.BRONZE_TROPHY.texture)
    private val other = SkullBuilder(Skulls.FOOTBALL.texture)

    private var currentRankingPos = 1
    private var nextRankingPos = 1

    fun inventory(page: Int, firstPage: Boolean): Inventory {
        if (firstPage) {
            this.currentRankingPos = 1
            this.nextRankingPos = 1
        }

        val size = if (this.goalGetters().size > 45) 54 else this.calculateSize()
        val inventory = InventoryBuilder(Holder(), size, "<yellow>Top Scorer")

        inventory.fill(ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).noName())

        val goalGetters = this.goalGetters()

        for ((slot, i) in ((page - 1) * 45 until min(this.goalGetters().size, (page - 1) * 45 + 45)).withIndex()) {
            inventory.set(this.goalGetterItem()
                .lore("<gray>Scorer<dark_gray>: <yellow>${goalGetters[i].name}",
                    "<gray>Goals<dark_gray>: <yellow>${goalGetters[i].goals}"), slot
            )

            this.nextRankingPos++
            if (goalGetters.getOrNull(i + 1) != null && goalGetters[i].goals != goalGetters[i + 1].goals) {
                this.currentRankingPos = this.nextRankingPos
            }
        }

        if (size == 54) {
            this.setPageItems(inventory, page)
        }

        return inventory.build()
    }

    private fun goalGetterItem(): SkullBuilder {
        if (this.currentRankingPos == 1) {
            this.gold.clearLore()
            return this.gold.name("<#D4AF37>#1")
        }
        if (this.currentRankingPos == 2) {
            this.silver.clearLore()
            return this.silver.name("<#C0C0C0>#2")
        }
        if (this.currentRankingPos == 3) {
            this.bronze.clearLore()
            return this.bronze.name("<#BF8970>#3")
        }
        this.other.clearLore()
        return this.other.name("<#FFFFFF>#${this.currentRankingPos}")
    }

    private fun setPageItems(inventory: InventoryBuilder, page: Int) {
        if (page == ceil(this.goalGetters().size / this.calculateSize().toDouble()).toInt()) {
            return
        }
        inventory.set(ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
            .name("<green>Next page")
            .data(Keys.NEXT_PAGE, PersistentDataType.INTEGER, page+1), this.calculateSize()+8
        )
    }

    private fun calculateSize(): Int {
        return min(9 * ceil(this.goalGetters().size / 9.0).toInt(), 45)
    }

    private fun goalGetters(): List<GoalGetter> {
        return EuroCup.tournamentAPI.goalGettersCache.sortedWith(compareBy<GoalGetter> { it.goals }.reversed().thenBy { it.name })
    }

    class Holder : InventoryHolder {
        override fun getInventory(): Inventory {
            return Bukkit.createInventory(null, 9)
        }
    }
}