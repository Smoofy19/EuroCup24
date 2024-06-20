package de.smoofy.eurocup.tournament.match

import de.smoofy.eurocup.EuroCup
import de.smoofy.eurocup.fan.Team
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player


/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 20.06.2024 - 14:53
 */

/**

 */
class MatchCommand : CommandExecutor {

    override fun onCommand(commandSender: CommandSender, p1: Command, p2: String, args: Array<out String>): Boolean {
        if (commandSender !is Player) {
            return true
        }
        val player = EuroCup.INSTANCE.playerManager.euroCupPlayer(commandSender)
        if (args.size != 2) {
            player.bukkitPlayer().sendMessage(MiniMessage.miniMessage().deserialize(EuroCup.PREFIX + "<gray>Use<dark_gray>: ${EuroCup.GRADIENT}/match <team> <team>"))
            val component = Component.text().append(MiniMessage.miniMessage().deserialize(EuroCup.PREFIX + "<gray>Use the <yellow>FIFA country codes <gray>(<red>CLICK<gray>)"))
                .clickEvent(ClickEvent.openUrl("https://wikipedia.org/wiki/List_of_FIFA_country_codes")).asComponent()
            player.bukkitPlayer().sendMessage(component)
            return true
        }
        val match = EuroCup.tournamentAPI.match(Team.code(args[0]), Team.code(args[1]))
        if (match == null) {
            player.bukkitPlayer().sendMessage(MiniMessage.miniMessage().deserialize(EuroCup.PREFIX + "<red>Could not find the match!"))
            return true
        }
        player.bukkitPlayer().openInventory(MatchInventory(match).inventory())
        return true
    }
}