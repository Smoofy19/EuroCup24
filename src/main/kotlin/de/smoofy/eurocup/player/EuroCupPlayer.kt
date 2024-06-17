package de.smoofy.eurocup.player

import de.smoofy.eurocup.EuroCup
import de.smoofy.eurocup.fan.Country
import de.smoofy.eurocup.utils.UUIDFetcher
import dev.httpmarco.evelon.Row
import net.kyori.adventure.text.Component
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
class EuroCupPlayer(val uuid: UUID) {

    var name: String

    var rank: Rank
    var country: Country

    var banned: Boolean
    var muted: Boolean

    @Row(ignore = true)
    var labyMod: Boolean

    init {
        this.name = UUIDFetcher.name(this.uuid)
        this.rank = EuroCup.INSTANCE.repository.query().match("uuid", this.uuid.toString()).findFirst()?.rank ?: Rank.VIEWER
        this.country = EuroCup.INSTANCE.repository.query().match("uuid", this.uuid.toString()).findFirst()?.country ?: Country.NONE
        this.banned = EuroCup.INSTANCE.repository.query().match("uuid", this.uuid.toString()).findFirst()?.banned ?: false
        this.muted = EuroCup.INSTANCE.repository.query().match("uuid", this.uuid.toString()).findFirst()?.muted ?: false
        this.labyMod = false
    }

    constructor(uuid: UUID, name: String, rank: Rank, country: Country, banned: Boolean, muted: Boolean) : this(uuid) {
        this.name = name
        this.rank = rank
        this.country = country
        this.banned = banned
        this.muted = muted
    }

    fun hasPriority(neededPriority: Int): Boolean {
        return neededPriority <= this.rank.priority
    }

    fun displayName(): Component {
        return Component.text(this.name, this.rank.color)
    }

    fun bukkitPlayer(): Player {
        return Bukkit.getPlayer(this.uuid)!!
    }

}
