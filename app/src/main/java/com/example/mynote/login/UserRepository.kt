package com.example.mynote.login

class UserRepository(private val userDao: UserDao) {
    val getAll = userDao.getAll()

    suspend fun insertAll(user: User) {
        userDao.insertAll(user)
    }

    suspend fun delete(user: User) {
        userDao.delete(user)
    }
}
