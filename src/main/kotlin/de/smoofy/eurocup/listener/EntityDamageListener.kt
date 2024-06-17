package de.smoofy.eurocup.listener

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent

/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 24.11.2023 - 17:27
 */

/**

 */

class EntityDamageListener : Listener {

    @EventHandler
    fun onDamage(event: EntityDamageEvent) {
        if (event.entity !is Player) {
            return
        }
        event.isCancelled = true
    }

}
