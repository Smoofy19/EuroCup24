package de.smoofy.eurocup.commands

import de.smoofy.eurocup.EuroCup
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
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

class SpeedCommand : CommandExecutor, TabCompleter {

    override fun onCommand(commandSender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        if (commandSender !is Player) {
            return true
        }
        val player: Player = commandSender
        if (args.isEmpty()) {
            player.sendMessage(MiniMessage.miniMessage().deserialize(EuroCup.PREFIX + "<gray>Use<dark_gray>: ${EuroCup.GRADIENT}/speed <1-5>"))
            return true
        }
        val speed = args[0].toIntOrNull()
        if (speed == null || speed < 1 || speed > 5) {
            player.sendMessage(MiniMessage.miniMessage().deserialize(EuroCup.PREFIX + "<gray>Use<dark_gray>: ${EuroCup.GRADIENT}/speed <1-5>"))
            return true
        }
        if (player.isFlying) {
            player.flySpeed = speed * 0.1f
            player.sendMessage(MiniMessage.miniMessage().deserialize(EuroCup.PREFIX + "<gray>Your fly speed has been set to <red>$speed<gray>."))
        } else {
            player.walkSpeed = speed * 0.2f
            player.sendMessage(MiniMessage.miniMessage().deserialize(EuroCup.PREFIX + "<gray>Your walk speed has been set to <red>$speed<gray>."))
        }
        return true
    }

    override fun onTabComplete(commandSender: CommandSender, p1: Command, p2: String, args: Array<out String>): MutableList<String> {
        if (commandSender !is Player) {
            return mutableListOf()
        }
        if (args.size == 1) {
            return mutableListOf("1", "2", "3", "4", "5")
        }
        return mutableListOf()
    }
}
