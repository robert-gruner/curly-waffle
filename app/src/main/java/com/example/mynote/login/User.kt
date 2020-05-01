package com.example.mynote.login

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "isLoggedIn") var isLoggedIn: Boolean = false
)
