package com.example.testapp.model

interface UserRepository {
    suspend fun registerUser(user: User): Boolean

    suspend fun loginUser(
        login: String,
        password: String,
    ): User?

    suspend fun updateUser(user: User)

    suspend fun deleteUser(user: User)
}
