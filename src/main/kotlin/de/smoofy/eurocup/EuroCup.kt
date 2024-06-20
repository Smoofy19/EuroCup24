package de.smoofy.eurocup

import de.smoofy.eurocup.commands.*
import de.smoofy.eurocup.fan.FanCommand
import de.smoofy.eurocup.fan.Team
import de.smoofy.eurocup.labymod.PluginMessageReceivedListener
import de.smoofy.eurocup.listener.*
import de.smoofy.eurocup.player.EuroCupPlayer
import de.smoofy.eurocup.player.EuroCupPlayerManager
import de.smoofy.eurocup.tablist.Tablist
import de.smoofy.eurocup.tournament.TournamentAPI
import de.smoofy.eurocup.tournament.goalgetter.GoalGetterInventory
import de.smoofy.eurocup.tournament.goalgetter.GoalGetterListener
import de.smoofy.eurocup.tournament.match.MatchCommand
import de.smoofy.eurocup.tournament.match.MatchesCommand
import de.smoofy.eurocup.tournament.match.MatchesListener
import dev.httpmarco.evelon.MariaDbLayer
import dev.httpmarco.evelon.Repository
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

    lateinit var goalGetterInventory: GoalGetterInventory

    val vanishedPlayers: MutableList<EuroCupPlayer> = mutableListOf()

    override fun onEnable() {
        INSTANCE = this

        this.playerManager = EuroCupPlayerManager()
        this.tablist = Tablist()
        this.repository = Repository.build(EuroCupPlayer::class.java).withLayer(MariaDbLayer::class.java).build()

        this.goalGetterInventory = GoalGetterInventory()

        Team.Cache().init()

        this.registerCommands()
        this.registerTabCompleter()
        this.registerListener()

        this.server.messenger.registerIncomingPluginChannel(this, "labymod3:main", PluginMessageReceivedListener())

        Bukkit.getWorlds().forEach { world ->
            world.setGameRule(GameRule.DO_WEATHER_CYCLE, false)
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false)
        }

        tournamentAPI = TournamentAPI()
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, { ->
            tournamentAPI.update()
        }, 0L, 20*60*15L)
    }

    private fun registerCommands() {
        getCommand("ban")?.setExecutor(BanCommand())
        getCommand("fan")?.setExecutor(FanCommand())
        getCommand("fly")?.setExecutor(FlyCommand())
        getCommand("match")?.setExecutor(MatchCommand())
        getCommand("matches")?.setExecutor(MatchesCommand())
        getCommand("mute")?.setExecutor(MuteCommand())
        getCommand("spawn")?.setExecutor(SpawnCommand())
        getCommand("speed")?.setExecutor(SpeedCommand())
        getCommand("top")?.setExecutor(TopCommand())
        getCommand("vanish")?.setExecutor(VanishCommand())
    }

    private fun registerTabCompleter() {
        getCommand("match")?.tabCompleter = MatchCommand()
        getCommand("top")?.tabCompleter = TopCommand()
        getCommand("speed")?.tabCompleter = SpeedCommand()
    }

    private fun registerListener() {
        val pluginManager = Bukkit.getPluginManager()
        pluginManager.registerEvents(AsyncChatListener(), this)
        pluginManager.registerEvents(BlockBreakListener(), this)
        pluginManager.registerEvents(BlockPlaceListener(), this)
        pluginManager.registerEvents(EntityDamageListener(), this)
        pluginManager.registerEvents(EntityDismountListener(), this)
        pluginManager.registerEvents(EntitySpawnListener(), this)
        pluginManager.registerEvents(FoodLevelChangeListener(), this)
        pluginManager.registerEvents(InventoryClickListener(), this)
        pluginManager.registerEvents(PlayerDropItemListener(), this)
        pluginManager.registerEvents(PlayerInteractListener(), this)
        pluginManager.registerEvents(PlayerJoinListener(), this)
        pluginManager.registerEvents(PlayerLoginListener(), this)
        pluginManager.registerEvents(PlayerMoveListener(), this)
        pluginManager.registerEvents(PlayerQuitListener(), this)
        pluginManager.registerEvents(ServerListPingListener(), this)

        pluginManager.registerEvents(GoalGetterListener(), this)
        pluginManager.registerEvents(MatchesListener(), this)
    }

    companion object {
        lateinit var INSTANCE: EuroCup
        lateinit var tournamentAPI: TournamentAPI
        var GRADIENT = "<gradient:#000000:#FF0000:#FFCC00>"
        var PREFIX = "<dark_gray>[${GRADIENT}EuroCup</gradient><dark_gray>] "
    }
}
