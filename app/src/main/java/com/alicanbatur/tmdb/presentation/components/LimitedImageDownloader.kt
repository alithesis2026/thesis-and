package com.alicanbatur.tmdb.presentation.components

import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import java.net.URL
import javax.inject.Singleton

@Singleton
class LimitedImageDownloader {
    private val semaphore = Semaphore(permits = 4)

    suspend fun downloadBytes(url: String): ByteArray? = semaphore.withPermit {
        runCatching { URL(url).readBytes() }.getOrNull()
    }
}
