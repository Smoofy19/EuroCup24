package de.smoofy.eurocup.utils

import de.smoofy.eurocup.EuroCup
import org.bukkit.NamespacedKey


/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 18.06.2024 - 19:23
 */

/**

 */
enum class Keys(val key: NamespacedKey) {

    PLAYER_VISIBILITY(NamespacedKey.fromString("player_visibility", EuroCup.INSTANCE)!!),
    MATCH(NamespacedKey.fromString("match", EuroCup.INSTANCE)!!),
    NEXT_PAGE(NamespacedKey.fromString("next_page", EuroCup.INSTANCE)!!),
    PREVIOUS_PAGE(NamespacedKey.fromString("previous_page", EuroCup.INSTANCE)!!)
}