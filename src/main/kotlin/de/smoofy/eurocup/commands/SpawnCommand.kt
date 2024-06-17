package de.smoofy.eurocup.commands

import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player


/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 17.06.2024 - 18:43
 */

/**

 */
class SpawnCommand : CommandExecutor {

    override fun onCommand(commandSender: CommandSender, p1: Command, p2: String, args: Array<out String>?): Boolean {
        if (commandSender !is Player) {
            return true
        }
        val player: Player = commandSender
        player.teleport(Location(player.world, 0.5, 100.5, 0.5, 0f, 0f))
        return true
    }
}