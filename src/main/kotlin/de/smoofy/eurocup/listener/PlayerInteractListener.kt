package de.smoofy.eurocup.listener

import de.smoofy.eurocup.EuroCup
import de.smoofy.eurocup.builder.ItemBuilder
import de.smoofy.eurocup.player.Rank
import de.smoofy.eurocup.utils.Keys
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.block.data.type.Stairs
import org.bukkit.entity.AbstractArrow
import org.bukkit.entity.Arrow
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.persistence.PersistentDataType

/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 24.11.2023 - 17:27
 */

/**

 */

class PlayerInteractListener : Listener {

    @EventHandler
    fun onAct(event: PlayerInteractEvent) {
        val player = EuroCup.INSTANCE.playerManager.euroCupPlayer(event.player)
        if (!player.hasPriority(Rank.ADMIN.priority) || player.bukkitPlayer().gameMode != GameMode.CREATIVE) {
            event.isCancelled = true
        }
        if (event.action == Action.RIGHT_CLICK_BLOCK || event.action == Action.RIGHT_CLICK_AIR) {
            if (event.item != null) {
                val value = event.item!!.itemMeta.persistentDataContainer.get(Keys.PLAYER_VISIBILITY.key, PersistentDataType.STRING)
                if (value != null) {
                    when (value) {
                        "all" -> {
                            player.bukkitPlayer().inventory.setItem(8, ItemBuilder(Material.RED_DYE)
                                .data(Keys.PLAYER_VISIBILITY, PersistentDataType.STRING, "none")
                                .name("<red>No players visible <gray>(right click)")
                                .build())

                            Bukkit.getOnlinePlayers().forEach { all -> player.bukkitPlayer().hidePlayer(EuroCup.INSTANCE, all) }
                        }
                        "none" -> {
                            player.bukkitPlayer().inventory.setItem(8, ItemBuilder(Material.YELLOW_DYE)
                                .data(Keys.PLAYER_VISIBILITY, PersistentDataType.STRING, "fans")
                                .name("<yellow>Fans visible <gray>(right click)")
                                .build())

                            Bukkit.getOnlinePlayers().forEach {
                                val all = EuroCup.INSTANCE.playerManager.euroCupPlayer(it)
                                if (player.team == all.team && !EuroCup.INSTANCE.vanishedPlayers.contains(all)) {
                                    player.bukkitPlayer().showPlayer(EuroCup.INSTANCE, it)
                                }
                            }
                        }
                        "fans" -> {
                            player.bukkitPlayer().inventory.setItem(8, ItemBuilder(Material.LIME_DYE)
                                .data(Keys.PLAYER_VISIBILITY, PersistentDataType.STRING, "all")
                                .name("<green>All players visible <gray>(right click)")
                                .build())

                            Bukkit.getOnlinePlayers().forEach {
                                val all = EuroCup.INSTANCE.playerManager.euroCupPlayer(it)
                                if (!EuroCup.INSTANCE.vanishedPlayers.contains(all)) {
                                    player.bukkitPlayer().showPlayer(EuroCup.INSTANCE, it)
                                }
                            }
                        }
                    }
                    return
                }
            }
        }
        if (event.action != Action.RIGHT_CLICK_BLOCK || event.clickedBlock == null) {
            return
        }
        val block: Block = event.clickedBlock!!
        if (block.type != Material.BIRCH_STAIRS && block.type != Material.BAMBOO_STAIRS) {
            return
        }
        if (!player.bukkitPlayer().isValid || player.bukkitPlayer().isSneaking) {
            return
        }
        val stairs: Stairs = block.blockData as Stairs
        val location = player.bukkitPlayer().location
        location.yaw = this.yaw(stairs.facing.oppositeFace)
        location.pitch = 0f
        player.bukkitPlayer().teleport(location)

        val arrow = block.world.spawnEntity(block.location.add(0.5, 0.2, 0.5), EntityType.ARROW) as Arrow
        player.chair = arrow
        arrow.isInvisible = true
        arrow.pickupStatus = AbstractArrow.PickupStatus.DISALLOWED
        arrow.addPassenger(player.bukkitPlayer())
    }

    private fun yaw(face: BlockFace): Float {
        return when (face) {
            BlockFace.NORTH -> 180f
            BlockFace.EAST -> 270f
            BlockFace.SOUTH -> 0f
            BlockFace.WEST -> 90f
            else -> 0f
        }
    }
}
