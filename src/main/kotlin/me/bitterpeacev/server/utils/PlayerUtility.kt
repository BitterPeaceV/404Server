package me.bitterpeacev.server.utils

import cn.nukkit.Player
import cn.nukkit.event.entity.EntityDamageByEntityEvent
import cn.nukkit.event.entity.EntityDamageEvent
import cn.nukkit.level.particle.DestroyBlockParticle
import cn.nukkit.level.particle.RedstoneParticle

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

    /**
     * ビームを撃つ
     *
     * @param player 撃つプレイヤー
     */
    fun shotBeam(player: Player) {
        val particle = RedstoneParticle(player.add(0.0, player.eyeHeight.toDouble()), 3)
        val increase = player.directionVector.normalize()
        for (i in 0 until 40) {
            val pos = particle.add(increase)

            val block = player.level.getBlock(pos)
            if (!block.canBeFlowedInto()) {
                player.level.addParticle(DestroyBlockParticle(block, block))

                return
            }

            particle.setComponents(pos.x, pos.y, pos.z)
            player.level.addParticle(particle)

            player.level.players.values.forEach {
                if (it.distance(pos) < 1.5 && it != player) {
                    val dmgEv = EntityDamageByEntityEvent(
                        player,
                        it,
                        EntityDamageEvent.DamageCause.PROJECTILE,
                        20f
                    )
                    it.attack(dmgEv)

                    return
                }
            }
        }
    }
}