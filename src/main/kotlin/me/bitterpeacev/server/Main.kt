package me.bitterpeacev.server

import cn.nukkit.Player
import cn.nukkit.plugin.PluginBase
import java.io.File

/**
 * プラグインクラス
 *
 * @author BitterPeaceV
 */
class Main : PluginBase() {

    override fun onEnable() {
        server.pluginManager.registerEvents(MainListener(this), this)

        loadWorlds()
    }

    override fun onDisable() {
        closeAllEntities()
    }

    /**
     * ワールドを全て読み込む
     */
    private fun loadWorlds() {
        File(server.dataPath + "worlds").listFiles()
            .filter { it.isDirectory && !server.isLevelLoaded(it.name) }
            .forEach { server.loadLevel(it.name) }
    }

    /**
     * プレイヤー以外のエンティティを消す
     */
    private fun closeAllEntities() {
        server.levels.values.forEach { level ->
            level.entities
                .filter { it !is Player }
                .forEach { it.close() }
        }
    }
}