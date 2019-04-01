package me.bitterpeacev.server

import cn.nukkit.event.EventHandler
import cn.nukkit.event.Listener
import cn.nukkit.event.player.PlayerJoinEvent

class MainListener(private val plugin: Main) : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player

        // プレイヤーをロビーにテレポートする
        player.server.scheduler.scheduleDelayedTask(
            plugin,
            { player.teleport(player.server.defaultLevel.safeSpawn) },
            1
        )
    }
}