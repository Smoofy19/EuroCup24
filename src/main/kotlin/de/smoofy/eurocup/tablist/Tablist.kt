package de.smoofy.eurocup.tablist

import com.google.common.collect.Maps
import de.smoofy.eurocup.EuroCup
import de.smoofy.eurocup.fan.Team
import de.smoofy.eurocup.player.EuroCupPlayer
import de.smoofy.eurocup.player.Rank
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.scoreboard.Scoreboard

/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 24.11.2023 - 17:27
 */

/**

 */

class Tablist {

    private val groups: MutableMap<Rank, String> = Maps.newConcurrentMap()

    init {
        Rank.entries.forEach { rank -> this.groups[rank] = rank.id }
    }

    fun set(euroCupPlayer: EuroCupPlayer) {
        val player = euroCupPlayer.bukkitPlayer()
        val scoreboard = player.scoreboard
        for (rank in this.groups.keys) {
            this.createTeam(scoreboard, this.groups[rank] + rank.rankName)
            if (scoreboard.getTeam(this.groups[rank] + rank.rankName) == null) continue
            scoreboard.getTeam(this.groups[rank] + rank.rankName)!!.prefix(Component.text(rank.prefix))
            scoreboard.getTeam(this.groups[rank] + rank.rankName)!!.color(rank.color)
        }
        for (onlinePlayer in EuroCup.INSTANCE.playerManager.onlinePlayers()) {
            val rank = onlinePlayer.rank
            val teamName = this.groups[rank] + rank.rankName
            var team = scoreboard.getTeam(teamName)
            if (team == null) {
                team = scoreboard.registerNewTeam(teamName)
            }
            var suffix = MiniMessage.miniMessage().deserialize(" <gray>[<red>${onlinePlayer.team.countryCode}<gray>]")
            if (onlinePlayer.team == Team.NONE) {
                suffix = Component.empty()
            }
            team.suffix(suffix)
            team.addEntry(onlinePlayer.bukkitPlayer().name)
        }
    }

    private fun createTeam(scoreboard: Scoreboard, name: String) {
        if (scoreboard.getTeam(name) != null) {
            return
        }
        scoreboard.registerNewTeam(name)
    }
}
