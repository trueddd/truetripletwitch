package com.github.trueddd.truetripletwitch.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class Routing(val name: String) : Parcelable {

    companion object {
        object Keys {
            const val MAIN = "main"
            const val STREAM = "stream"
        }
    }

    @Parcelize
    object Main : Routing(Keys.MAIN)

    @Parcelize
    class Stream(val channel: String) : Routing(Keys.STREAM)
}
