package com.ping.common.util

import java.io.BufferedReader
import java.io.InputStreamReader

object UrlUtil {
    fun expandShortUrl(shortUrl: String): String {
        val process = ProcessBuilder("curl", "-L", "-s", "-o", "/dev/null", "-w", "%{url_effective}", shortUrl).start()

        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val expandedUrl = reader.readLine()

        reader.close()
        return expandedUrl
    }
}