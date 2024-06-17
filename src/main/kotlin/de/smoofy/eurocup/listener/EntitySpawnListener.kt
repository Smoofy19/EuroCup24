package de.smoofy.eurocup.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntitySpawnEvent

/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 24.11.2023 - 17:27
 */

/**

 */

class EntitySpawnListener : Listener {

    @EventHandler
    fun onSpawn(event: EntitySpawnEvent) {
        event.isCancelled = true
    }

}
