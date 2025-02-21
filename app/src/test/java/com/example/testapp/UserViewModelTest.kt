package com.example.testapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testapp.model.Logger
import com.example.testapp.model.User
import com.example.testapp.model.UserService
import com.example.testapp.room.UserDao
import com.example.testapp.room.UserDbEntity
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var userService: UserService
    private lateinit var userViewModel: UserViewModel
    private val userDao: UserDao = mockk()
    private val logger: Logger = mockk()

    @Before
    fun setUp() {
        userService = UserService(userDao, logger)
        userViewModel = UserViewModel(userService)

        // Мокаем методы логгера
        every { logger.e(any(), any()) } just Runs
    }

    @Test
    fun `registerUser should return true on successful registration`() =
        runTest {
            // Arrange
            val user = User(0, "login", "password", "test")
            coEvery { userDao.registerUser(any()) } returns Unit

            // Act
            val result = userViewModel.registerUser(user)

            // Assert
            assertTrue(result)
            coVerify { userDao.registerUser(any()) }
        }

    @Test
    fun `registerUser should return false on registration failure`() =
        runTest {
            // Arrange
            val user = User(0, "login", "password", "Alexey")
            coEvery { userDao.registerUser(any()) } throws Exception("Username already exists")

            // Act
            val result = userViewModel.registerUser(user)

            // Assert
            assertFalse(result)
            coVerify { userDao.registerUser(any()) }
        }

    @Test
    fun `loginUser should return username on successful login`() =
        runTest {
            // Arrange
            val login = "testLogin"
            val password = "testPassword"
            val user = User(0, login, password, "Alexey")
            coEvery { userDao.loginUser(login, password) } returns UserDbEntity.fromUserInput(user)

            // Act
            val result = userViewModel.loginUser(login, password)

            // Assert
            assertEquals("Alexey", result)
            coVerify { userDao.loginUser(login, password) }
        }

    @Test
    fun `loginUser should return null on login failure`() =
        runTest {
            // Arrange
            val login = "testLogin"
            val password = "testPassword"
            coEvery { userDao.loginUser(login, password) } returns null

            // Act
            val result = userViewModel.loginUser(login, password)

            // Assert
            assertNull(result)
            coVerify { userDao.loginUser(login, password) }
        }

    @Test
    fun `updateUser should call updateUser in userService`() =
        runTest {
            // Arrange
            val user = User(0, "testLogin", "testPassword", "Alexey")
            coEvery { userDao.updateUser(any()) } just Runs

            // Act
            userViewModel.updateUser(user)

            // Assert
            coVerify { userDao.updateUser(UserDbEntity.fromUserInput(user)) }
        }

    @Test
    fun `deleteUser should call deleteUser in userService`() =
        runTest {
            // Arrange
            val user = User(0, "testLogin", "testPassword", "Alexey")
            coEvery { userDao.deleteUser(any()) } just Runs

            // Act
            userViewModel.deleteUser(user)

            // Assert
            coVerify { userDao.deleteUser(UserDbEntity.fromUserInput(user)) }
        }
}
