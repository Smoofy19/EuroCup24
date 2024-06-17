package de.smoofy.eurocup.player

import de.smoofy.eurocup.EuroCup
import de.smoofy.eurocup.fan.Country
import de.smoofy.eurocup.utils.UUIDFetcher
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 24.11.2023 - 17:27
 */

/**

 */

class EuroCupPlayerManager {

    private val euroCupPlayerCache: MutableMap<Any, EuroCupPlayer> = mutableMapOf()

    fun init(uuid: UUID, banned: Boolean, muted: Boolean) {
        if (this.exists(uuid)) {
            return
        }
        EuroCup.INSTANCE.repository.query().create(EuroCupPlayer(
            uuid, UUIDFetcher.name(uuid), Rank.VIEWER, Country.NONE, banned = banned, muted = muted)
        )
    }

    fun exists(uuid: UUID): Boolean {
        return EuroCup.INSTANCE.repository.query().match("uuid", uuid).exists()
    }

    fun update(euroCupPlayer: EuroCupPlayer) {
        EuroCup.INSTANCE.repository.query().match("uuid", euroCupPlayer.uuid).update(euroCupPlayer)
    }

    fun euroCupPlayer(player: Player): EuroCupPlayer {
        if (this.euroCupPlayerCache.containsKey(player)) return this.euroCupPlayerCache[player]!!
        return EuroCupPlayer(player.uniqueId)
    }

    fun euroCupPlayer(uuid: UUID): EuroCupPlayer {
        if (this.euroCupPlayerCache.containsKey(uuid)) return this.euroCupPlayerCache[uuid]!!
        return EuroCupPlayer(uuid)
    }

    fun onlinePlayers(): Collection<EuroCupPlayer> {
        val players = mutableListOf<EuroCupPlayer>()
        Bukkit.getOnlinePlayers().forEach { player -> players.add(this.euroCupPlayer(player)) }
        return players
    }

    fun cacheEuroCupPlayer(euroCupPlayer: EuroCupPlayer) {
        this.euroCupPlayerCache[euroCupPlayer.uuid] = euroCupPlayer
        this.euroCupPlayerCache[euroCupPlayer.name.lowercase()] = euroCupPlayer
        this.euroCupPlayerCache[euroCupPlayer.bukkitPlayer()] = euroCupPlayer
    }
}
