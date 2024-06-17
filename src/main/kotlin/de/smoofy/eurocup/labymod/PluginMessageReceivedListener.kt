package de.smoofy.eurocup.labymod

import de.smoofy.eurocup.EuroCup
import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener


/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 17.06.2024 - 16:00
 */

/**

 */
class PluginMessageReceivedListener : PluginMessageListener {

    override fun onPluginMessageReceived(channel: String, player: Player, message: ByteArray) {
        if (channel != "labymod3:main") {
            return
        }
        EuroCup.INSTANCE.playerManager.euroCupPlayer(player).labyMod = true
    }
}