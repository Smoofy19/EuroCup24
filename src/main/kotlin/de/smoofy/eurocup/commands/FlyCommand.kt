package de.smoofy.eurocup.commands

import de.smoofy.eurocup.EuroCup
import de.smoofy.eurocup.player.Rank
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 24.11.2023 - 17:27
 */

/**

 */

class FlyCommand : CommandExecutor {

    override fun onCommand(commandSender: CommandSender, p1: Command, p2: String, args: Array<out String>?): Boolean {
        if (commandSender !is Player) {
            return true
        }
        val player = EuroCup.INSTANCE.playerManager.euroCupPlayer(commandSender)
        if (!player.hasPriority(Rank.ADMIN.priority)) {
            return true
        }
        if (player.bukkitPlayer().allowFlight) {
            player.bukkitPlayer().isFlying = false
            player.bukkitPlayer().allowFlight = false
        } else {
            player.bukkitPlayer().allowFlight = true
            player.bukkitPlayer().isFlying = true
        }
        return true
    }
}
