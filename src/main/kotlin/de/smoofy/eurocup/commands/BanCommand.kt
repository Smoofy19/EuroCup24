package de.smoofy.eurocup.commands

import de.smoofy.eurocup.EuroCup
import de.smoofy.eurocup.player.Rank
import de.smoofy.eurocup.utils.UUIDFetcher
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
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
 * Created - 17.06.2024 - 18:45
 */

/**

 */
class BanCommand : CommandExecutor {

    override fun onCommand(commandSender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        if (commandSender !is Player) {
            return true
        }
        val player = EuroCup.INSTANCE.playerManager.euroCupPlayer(commandSender)
        if (!player.hasPriority(Rank.ADMIN.priority)) {
            return true
        }
        if (args.isEmpty()) {
            player.bukkitPlayer().sendMessage(MiniMessage.miniMessage().deserialize(EuroCup.PREFIX + "<gray>Verwende<dark_gray>: ${EuroCup.GRADIENT}/ban <Spieler>"))
            return true
        }
        val name = args[0]
        val uuid = UUIDFetcher.uuid(name)
        if (uuid == null) {
            player.bukkitPlayer().sendMessage(MiniMessage.miniMessage().deserialize(EuroCup.PREFIX + "<gray>Der Spieler <green>$name <gray>existiert nicht."))
            return true
        }
        if (EuroCup.INSTANCE.playerManager.exists(uuid)) {
            val target = EuroCup.INSTANCE.playerManager.euroCupPlayer(uuid)
            target.banned = !target.banned
            player.bukkitPlayer().sendMessage(MiniMessage.miniMessage().deserialize(EuroCup.PREFIX + "<gray>Der Spieler <green>${target.name} <gray>wurde ${if (target.banned) "<red>gebannt" else "<green>entbannt"}."))
            EuroCup.INSTANCE.playerManager.update(target)
            if (Bukkit.getPlayer(uuid) != null) {
                target.bukkitPlayer().kick(Component.text("You have been banned from the server!", NamedTextColor.RED))
            }
        } else {
            EuroCup.INSTANCE.playerManager.init(uuid, true, false)
        }
        return true
    }
}