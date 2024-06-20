package de.smoofy.eurocup.commands

import de.smoofy.eurocup.EuroCup
import de.smoofy.eurocup.player.Rank
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player


/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 18.06.2024 - 12:08
 */

/**

 */
class VanishCommand : CommandExecutor {

    override fun onCommand(commandSender: CommandSender, p1: Command, p2: String, args: Array<out String>?): Boolean {
        if (commandSender !is Player) {
            return true
        }
        val player = EuroCup.INSTANCE.playerManager.euroCupPlayer(commandSender)
        if (!player.hasPriority(Rank.ADMIN.priority)) {
            return true
        }
        if (EuroCup.INSTANCE.vanishedPlayers.contains(player)) {
            EuroCup.INSTANCE.vanishedPlayers.remove(player)
            Bukkit.getOnlinePlayers().forEach { all -> all.showPlayer(EuroCup.INSTANCE, player.bukkitPlayer()) }
            player.bukkitPlayer().sendMessage(MiniMessage.miniMessage().deserialize("${EuroCup.PREFIX} <red>Du bist nun nicht mehr im Vanish!"))
        } else {
            EuroCup.INSTANCE.vanishedPlayers.add(player)
            Bukkit.getOnlinePlayers().forEach { all -> all.hidePlayer(EuroCup.INSTANCE, player.bukkitPlayer()) }
            player.bukkitPlayer().sendMessage(MiniMessage.miniMessage().deserialize("${EuroCup.PREFIX} <green>Du bist nun im Vanish!"))
        }
        return true
    }
}