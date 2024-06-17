package de.smoofy.eurocup

import de.smoofy.eurocup.commands.*
import de.smoofy.eurocup.fan.FanCommand
import de.smoofy.eurocup.labymod.PluginMessageReceivedListener
import de.smoofy.eurocup.listener.*
import de.smoofy.eurocup.player.EuroCupPlayer
import de.smoofy.eurocup.player.EuroCupPlayerManager
import de.smoofy.eurocup.tablist.Tablist
import dev.httpmarco.evelon.MariaDbLayer
import dev.httpmarco.evelon.Repository
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.GameRule
import org.bukkit.plugin.java.JavaPlugin

/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 24.11.2023 - 17:27
 */

/**

 */

class EuroCup : JavaPlugin() {

    lateinit var playerManager: EuroCupPlayerManager
    lateinit var tablist: Tablist
    lateinit var repository: Repository<EuroCupPlayer>

    override fun onEnable() {
        INSTANCE = this

        miniMessage = MiniMessage.miniMessage()

        this.playerManager = EuroCupPlayerManager()
        this.tablist = Tablist()
        this.repository = Repository.build(EuroCupPlayer::class.java).withLayer(MariaDbLayer::class.java).build()

        this.registerCommands()
        this.registerListener()

        this.server.messenger.registerIncomingPluginChannel(this, "labymod3:main", PluginMessageReceivedListener())

        Bukkit.getWorlds().forEach { world ->
            world.setGameRule(GameRule.DO_WEATHER_CYCLE, false)
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false)
        }
    }

    private fun registerCommands() {
        getCommand("ban")?.setExecutor(BanCommand())
        getCommand("fan")?.setExecutor(FanCommand())
        getCommand("fly")?.setExecutor(FlyCommand())
        getCommand("mute")?.setExecutor(MuteCommand())
        getCommand("spawn")?.setExecutor(SpawnCommand())
        getCommand("speed")?.setExecutor(SpeedCommand())
    }

    private fun registerListener() {
        val pluginManager = Bukkit.getPluginManager()
        pluginManager.registerEvents(AsyncChatListener(), this)
        pluginManager.registerEvents(BlockBreakListener(), this)
        pluginManager.registerEvents(BlockPlaceListener(), this)
        pluginManager.registerEvents(EntityDamageListener(), this)
        pluginManager.registerEvents(EntitySpawnListener(), this)
        pluginManager.registerEvents(FoodLevelChangeListener(), this)
        pluginManager.registerEvents(PlayerInteractListener(), this)
        pluginManager.registerEvents(PlayerJoinListener(), this)
        pluginManager.registerEvents(PlayerLoginListener(), this)
        pluginManager.registerEvents(PlayerMoveListener(), this)
        pluginManager.registerEvents(PlayerQuitListener(), this)
        pluginManager.registerEvents(ServerListPingListener(), this)
    }

    companion object {
        lateinit var INSTANCE: EuroCup
        lateinit var miniMessage: MiniMessage
        var GRADIENT = "<gradient:#000000:#FF0000:#FFCC00>"
        var PREFIX = "<dark_gray>[${GRADIENT}EuroCup</gradient><dark_gray>] "
    }
}
