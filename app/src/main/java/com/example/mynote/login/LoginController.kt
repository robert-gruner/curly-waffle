package com.example.mynote.login

import android.view.View

object LoginController {
    private val hideableElements = mutableListOf<View>()
    private lateinit var userViewModel: UserViewModel
    private lateinit var currentUser: User

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
                currentUser = User(name = name, isLoggedIn = true)
                userViewModel.insertAll(currentUser)
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

    fun registerModel(viewModel: UserViewModel) {
        userViewModel = viewModel
    }

    fun checkUsers(allUsers: List<User>): Boolean {
        if (this::currentUser.isInitialized) {
            return currentUser.isLoggedIn
        }
        isLoggedIn = allUsers.any { it.isLoggedIn }
        if (isLoggedIn) {
            currentUser = allUsers.last { it.isLoggedIn }
        }
        return isLoggedIn
    }

    fun logoutCurrent() {
        isLoggedIn = false
        currentUser.isLoggedIn = false
        userViewModel.delete(currentUser)
    }
}
