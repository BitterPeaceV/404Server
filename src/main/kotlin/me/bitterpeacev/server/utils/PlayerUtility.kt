package me.bitterpeacev.server.utils

import cn.nukkit.Player

/**
 * プレイヤーに関するユーティリティ
 *
 * @author BitterPeaceV
 */
object PlayerUtility {

    /**
     * プレイヤーの状態を初期化する
     *
     * @param player プレイヤー
     */
    fun clearStatus(player: Player) {
        player.gamemode = player.server.defaultGamemode
        player.inventory.clearAll()
        player.removeAllEffects()
        player.extinguish()
        player.maxHealth = 20
        player.health = player.maxHealth.toFloat()
        player.foodData.level = player.foodData.maxLevel
        player.nameTag = player.name
        player.displayName = player.name
    }
}