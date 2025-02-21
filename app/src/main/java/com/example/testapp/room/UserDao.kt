package com.example.testapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT) // Прекратит выполнение, если есть конфликт
    suspend fun registerUser(userDbEntity: UserDbEntity)

    @Query("SELECT * FROM users WHERE login = :login AND password = :password LIMIT 1")
    suspend fun loginUser(
        login: String,
        password: String,
    ): UserDbEntity?

    @Update
    suspend fun updateUser(userDbEntity: UserDbEntity)

    @Delete(entity = UserDbEntity::class)
    suspend fun deleteUser(userDbEntity: UserDbEntity)
}
