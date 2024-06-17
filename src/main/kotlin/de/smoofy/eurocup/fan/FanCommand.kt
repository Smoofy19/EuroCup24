package de.smoofy.eurocup.fan

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
 * Created - 17.06.2024 - 20:14
 */

/**

 */
class FanCommand : CommandExecutor {

    private val fanManager = FanManager()

    override fun onCommand(commandSender: CommandSender, p1: Command, p2: String, args: Array<out String>?): Boolean {
        if (commandSender !is Player) {
            return true
        }
        val player = EuroCup.INSTANCE.playerManager.euroCupPlayer(commandSender)
        player.bukkitPlayer().openInventory(fanManager.fanInventory.inventory(player))
        return true
    }
}