package com.example.mynote.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository
    val getAll: LiveData<List<User>>

    init {
        val userDao = UserDatabase.getInstance(application).userDao()
        repository = UserRepository(userDao)
        getAll = repository.getAll
    }

    fun insertAll(user: User) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertAll(user)
    }

    fun delete(user: User) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(user)
    }

}
