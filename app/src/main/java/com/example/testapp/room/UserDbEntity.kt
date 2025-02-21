package com.example.testapp.room

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.testapp.model.User

@Entity(
    tableName = "users",
    indices = [
        Index("login", unique = true),
    ],
)
data class UserDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    var login: String,
    var password: String,
    var username: String,
) {
    fun toUser(): User = User(id = id, login = login, password = password, username = username)

    companion object {
        fun fromUserInput(user: User): UserDbEntity =
            UserDbEntity(
                id = user.id,
                login = user.login,
                password = user.password,
                username = user.username,
            )
    }
}
