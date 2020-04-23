package com.example.mynote

import android.util.Log
import android.view.View

object UserController {
    private val hideableElements = mutableListOf<View>()
    var isLoggedIn = false
        set(value) {
            field = value
            updateHideableElements()
        }

    fun registerHideableElement(view: View) {
        hideableElements.add(view)
        updateHideableElements()
    }

    private fun updateHideableElements() {
        Log.d(UserController::class.java.simpleName, hideableElements.size.toString())
        hideableElements.forEach {
            if (isLoggedIn) {
                it.visibility = View.VISIBLE
            } else {
                it.visibility = View.GONE
            }
        }
    }
}
