package com.github.trueddd.twitch.data

import androidx.room.Entity

@Entity(
    tableName = "users",
    primaryKeys = ["id"],
)
data class User(
    val id: String,
    val login: String,
    val displayName: String,
    val email: String,
    val profileImageUrl: String,
)
