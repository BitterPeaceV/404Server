package me.bitterpeacev.server

import cn.nukkit.utils.TextFormat
import java.io.File
import java.nio.charset.StandardCharsets
import java.util.*

/**
 * メッセージを管理するクラス
 *
 * @author BitterPeaceV
 */
object Messages {

    private val messages = mutableMapOf<String, String>()

    /**
     * .propertiesファイルからメッセージを読み込む
     *
     * @param file メッセージがのキーと値が記載されているファイル
     */
    fun loadMessages(file: File) {
        if (file.isFile && getSuffix(file.name) == "properties") {
            file.bufferedReader(StandardCharsets.UTF_8).use { reader ->
                val properties = Properties()
                properties.load(reader)

                properties.forEach { key, value -> messages[key.toString()] = value.toString() }
            }
        }
    }

    /**
     * キーからメッセージを取得する
     *
     * @param key    メッセージのキー
     * @param params 置換用の文字列
     *
     * @return 置換後の文字列。ただしキーが正しくない場合はkeyが返る
     */
    fun getMessage(key: String, params: Array<String> = emptyArray()): String {
        var message = messages[key] ?: key

        message = message.replace("&n", "\n")
        if (params.isNotEmpty()) {
            for (i in 0 until params.size) {
                message = message.replace("%${i + 1}", params[i])
            }
        }

        return TextFormat.colorize(message)
    }

    /**
     * ファイルの拡張子を返す
     *
     * @param fileName ファイル名
     *
     * @return 拡張子
     */
    private fun getSuffix(fileName: String): String {
        val point = fileName.lastIndexOf(".")

        return if (point != -1) fileName.substring(point + 1) else fileName
    }
}