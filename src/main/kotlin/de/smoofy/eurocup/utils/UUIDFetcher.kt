package de.smoofy.eurocup.utils

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.net.URLConnection
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.regex.Pattern

/*
 * Copyright ©
 * @author - Smoofy
 * @GitHub - https://github.com/Smoofy19
 * @Twitter - https://twitter.com/Smoofy19
 * Created - 24.11.2023 - 17:27
 */

/**

 */

class UUIDFetcher {

    companion object {
        private val NAME_PATTERN = Pattern.compile(",\\s*\"name\"\\s*:\\s*\"(.*?)\"")
        private val UUID_PATTERN = Pattern.compile("\"id\"\\s*:\\s*\"(.*?)\"")

        private val nameCache: MutableMap<UUID, String> = mutableMapOf()
        private val uuidCache: MutableMap<String, UUID> = mutableMapOf()

        fun name(uuid: UUID): String {
            if (nameCache.containsKey(uuid)) {
                return nameCache[uuid]!!
            }
            val url = callURL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString().replace("-", "")) ?: return "???"
            val matcher = NAME_PATTERN.matcher(url)
            if (matcher.find()) {
                val name = matcher.group(1)
                nameCache[uuid] = name
                return name
            }
            return "???"
        }

        fun uuid(name: String): UUID? {
            val finalName = name.lowercase()
            if (uuidCache.containsKey(name)) {
                return uuidCache[name]!!
            }
            val url = callURL("https://api.mojang.com/users/profiles/minecraft/$finalName") ?: return null
            val matcher = UUID_PATTERN.matcher(url)
            if (matcher.find()) {
                val uuid = matcher.group(1)
                val uuidObject = UUID.fromString(uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" +
                        uuid.substring(12, 16) + "-" + uuid.substring(16, 20) + "-" + uuid.substring(20, 32))
                uuidCache[finalName] = uuidObject
                return uuidObject
            }
            return null
        }

        private fun callURL(url: String): String? {
            val stringBuilder = StringBuilder()
            val urlConnection: URLConnection
            val bufferedReader: BufferedReader
            val inputStreamReader: InputStreamReader
            try {
                urlConnection = URL(url).openConnection()
                urlConnection.readTimeout = 60*1000
                if (urlConnection.getInputStream() != null) {
                    inputStreamReader = InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8)
                    bufferedReader = BufferedReader(inputStreamReader)
                    var line: String? = bufferedReader.readLine()
                    while (line != null) {
                        stringBuilder.append(line).append("\n")
                        line = bufferedReader.readLine()
                    }
                    bufferedReader.close()
                    inputStreamReader.close()
                }
            } catch (e: Exception) {
                return null
            }
            return stringBuilder.toString()
        }
    }

}
