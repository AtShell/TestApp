package com.example.testapp

import androidx.lifecycle.ViewModel
import com.example.testapp.model.User
import com.example.testapp.model.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserViewModel
    @Inject
    constructor(
        private val userService: UserService,
    ) : ViewModel() {
        suspend fun registerUser(user: User): Boolean =
            withContext(Dispatchers.IO) {
                return@withContext userService.registerUser(user)
            }

        suspend fun loginUser(
            login: String,
            password: String,
        ): String? =
            withContext(Dispatchers.IO) {
                val user = userService.loginUser(login, password)
                return@withContext user?.username
            }

        suspend fun updateUser(user: User) =
            withContext(Dispatchers.IO) {
                userService.updateUser(user)
            }

        suspend fun deleteUser(user: User) =
            withContext(Dispatchers.IO) {
                userService.deleteUser(user)
            }
    }
