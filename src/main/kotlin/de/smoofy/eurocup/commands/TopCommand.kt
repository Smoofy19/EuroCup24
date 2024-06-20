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
 * Created - 20.06.2024 - 13:19
 */

/**

 */
class TopCommand : CommandExecutor {

    override fun onCommand(commandSender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        if (commandSender !is Player) {
            return true
        }
        val player = EuroCup.INSTANCE.playerManager.euroCupPlayer(commandSender)
        if (args.isEmpty()) {

            return true
        }
        if (args[0].equals("scorer", ignoreCase = true)) {
            player.bukkitPlayer().openInventory(EuroCup.INSTANCE.goalGetterInventory.inventory(1))
            return true
        }
        if (args[0].equals("tabel", ignoreCase = true)) {
            //TODO
            return true
        }
        return true
    }
}