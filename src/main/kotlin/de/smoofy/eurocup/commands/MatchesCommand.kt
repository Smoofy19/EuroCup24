package de.smoofy.eurocup.commands

import de.smoofy.eurocup.EuroCup
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player


/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 18.06.2024 - 12:48
 */

/**

 */
class MatchesCommand : CommandExecutor {

    override fun onCommand(commandSender: CommandSender, p1: Command, p2: String, args: Array<out String>?): Boolean {
        if (commandSender !is Player) {
            return true
        }
        val player = EuroCup.INSTANCE.playerManager.euroCupPlayer(commandSender)

        return true
    }
}