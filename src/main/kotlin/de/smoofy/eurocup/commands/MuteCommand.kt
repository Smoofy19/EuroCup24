package de.smoofy.eurocup.commands

import de.smoofy.eurocup.EuroCup
import de.smoofy.eurocup.player.Rank
import de.smoofy.eurocup.utils.UUIDFetcher
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
class MuteCommand : CommandExecutor {

    override fun onCommand(commandSender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        if (commandSender !is Player) {
            return true
        }
        val player = EuroCup.INSTANCE.playerManager.euroCupPlayer(commandSender)
        if (!player.hasPriority(Rank.ADMIN.priority)) {
            return true
        }
        if (args.isEmpty()) {
            player.bukkitPlayer().sendMessage(EuroCup.miniMessage.deserialize(EuroCup.PREFIX + "<gray>Verwende<dark_gray>: ${EuroCup.GRADIENT}/mute <Spieler>"))
            return true
        }
        val name = args[0]
        val uuid = UUIDFetcher.uuid(name)
        if (uuid == null) {
            player.bukkitPlayer().sendMessage(EuroCup.miniMessage.deserialize(EuroCup.PREFIX + "<gray>Der Spieler <green>$name <gray>existiert nicht."))
            return true
        }
        if (EuroCup.INSTANCE.playerManager.exists(uuid)) {
            val target = EuroCup.INSTANCE.playerManager.euroCupPlayer(uuid)
            target.muted = !target.muted
            player.bukkitPlayer().sendMessage(EuroCup.miniMessage.deserialize(EuroCup.PREFIX + "<gray>Der Spieler <green>${target.name} <gray>wurde ${if (target.muted) "<red>gemuted" else "<green>entmuted"}."))
            EuroCup.INSTANCE.playerManager.update(target)
        } else {
            EuroCup.INSTANCE.playerManager.init(uuid, false, true)
        }
        return true
    }
}