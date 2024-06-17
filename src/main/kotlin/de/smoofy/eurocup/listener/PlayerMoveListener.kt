package de.smoofy.eurocup.listener

import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Sign
import org.bukkit.block.sign.Side
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent


/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 17.06.2024 - 16:55
 */

/**

 */
class PlayerMoveListener : Listener {

    @EventHandler
    fun onMove(event: PlayerMoveEvent) {
        val player = event.player
        if (player.location.block.type != Material.OAK_PRESSURE_PLATE) {
            return
        }
        val block = player.location.subtract(0.0, 2.0, 0.0).block
        if (block.type != Material.OAK_SIGN && block.type != Material.OAK_WALL_SIGN) {
            return
        }
        val sign: Sign = block.state as Sign
        val lines = this.lines(sign)
        if (lines[0] != "[Teleport]") {
            return
        }
        val x = lines[1].split(";")[0].toDouble() + 0.5
        val y = lines[1].split(";")[1].toDouble() + 0.5
        val z = lines[1].split(";")[2].toDouble() + 0.5
        val location = Location(player.world, x, y, z, 0.0f, 0.0f)
        player.teleport(location)
    }

    private fun lines(sign: Sign): List<String> {
        val lines: MutableList<String> = mutableListOf()
        for (line in sign.getSide(Side.FRONT).lines()) {
            lines.add(PlainTextComponentSerializer.plainText().serialize(line))
        }
        return lines
    }

}