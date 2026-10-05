package com.alicanbatur.tmdb.presentation.movielist

object ScrollPositionBus {
    private val listeners = mutableListOf<(Int) -> Unit>()

    fun addOnScrollChangedListener(listener: (Int) -> Unit) {
        listeners.add(listener)
    }

    fun removeOnScrollChangedListener(listener: (Int) -> Unit) {
        listeners.remove(listener)
    }

    fun notifyScroll(position: Int) {
        listeners.forEach { it(position) }
    }
}
