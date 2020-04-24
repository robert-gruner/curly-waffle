package com.example.mynote

import android.view.View

object LoginController {
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

    fun checkCredentials(name: String, password: String): Boolean {
        return arrayOf(name, password)
            .none { it.isEmpty() }
            .apply {
                isLoggedIn = this
            }
    }

    private fun updateHideableElements() {
        hideableElements.forEach {
            if (isLoggedIn) {
                it.visibility = View.VISIBLE
            } else {
                it.visibility = View.GONE
            }
        }
    }
}
