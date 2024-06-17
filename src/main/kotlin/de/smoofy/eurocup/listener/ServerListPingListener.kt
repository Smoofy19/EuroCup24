package de.smoofy.eurocup.listener

import de.smoofy.eurocup.EuroCup
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerListPingEvent
import java.util.*


/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 17.06.2024 - 23:16
 */

/**

 */
class ServerListPingListener : Listener {

    @EventHandler
    fun onPing(event: ServerListPingEvent) {
        val id = Random().nextInt(3)
        var motd = "                 <gradient:#000000:#FF0000:#FFCC00><b>EuroCup 24</b></gradient> <dark_gray>- <yellow>[1.8-1.20.6]"
        motd = when (id) {
            0 -> "$motd\n     <green>Watch the EuroCup matches with your friends!"
            1 -> "$motd\n                     <aqua>/fan<gray>｜<aqua>/speed<gray>｜<aqua>/spawn<gray>"
            else -> "$motd\n                <#7289da>https://discord.gg/DUAWyCFZyQ"
        }
        event.motd(EuroCup.miniMessage.deserialize(motd))
    }
}