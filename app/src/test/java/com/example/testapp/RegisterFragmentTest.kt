package com.example.testapp
import com.example.testapp.model.Logger
import com.example.testapp.model.UserService
import com.example.testapp.room.UserDao
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RegisterFragmentTest {
    private lateinit var fragment: RegisterFragment
    private lateinit var userService: UserService
    private lateinit var userViewModel: UserViewModel
    private val userDao: UserDao = mockk()
    private val logger: Logger = mockk()

    @Before
    fun setUp() {
        userService = UserService(userDao, logger)
        userViewModel = UserViewModel(userService)
        fragment = RegisterFragment(userViewModel)
    }

    @Test
    fun validateInputs_emptyLogin_returnsFalseWithErrorMessage() {
        val (isValid, errorMessage) = fragment.validateInputs("", "username", "password", "password") // Пустой логин

        assertEquals(false, isValid)
        assertEquals("Ошибка: Пустая строка логина", errorMessage)
    }

    @Test
    fun validateInputs_emptyUsername_returnsFalseWithErrorMessage() {
        val (isValid, errorMessage) = fragment.validateInputs("login", "", "password", "password") // Пустое имя пользователя

        assertEquals(false, isValid)
        assertEquals("Ошибка: Пустая строка имени пользователя", errorMessage)
    }

    @Test
    fun validateInputs_emptyPassword_returnsFalseWithErrorMessage() {
        val (isValid, errorMessage) = fragment.validateInputs("login", "username", "", "password") // Пустой пароль

        assertEquals(false, isValid)
        assertEquals("Ошибка: Пустая строка пароля", errorMessage)
    }

    @Test
    fun validateInputs_emptyPasswordRepeat_returnsFalseWithErrorMessage() {
        val (isValid, errorMessage) = fragment.validateInputs("login", "username", "password", "") // Пустой повтор пароля

        assertEquals(false, isValid)
        assertEquals("Ошибка: Пустая строка повтора пароля", errorMessage)
    }

    @Test
    fun validateInputs_passwordsDoNotMatch_returnsFalseWithErrorMessage() {
        val (isValid, errorMessage) = fragment.validateInputs("login", "username", "password", "differentPassword") // Пароли не совпадают

        assertEquals(false, isValid)
        assertEquals("Ошибка: Пароли не совпадают", errorMessage)
    }

    @Test
    fun validateInputs_validInputs_returnsTrueWithEmptyErrorMessage() {
        val (isValid, errorMessage) = fragment.validateInputs("login", "username", "password", "password") // Корректные данные

        assertEquals(true, isValid)
        assertEquals("", errorMessage) // Ошибки быть не должно
    }

    @Test
    fun validateInputs_invalidCharactersInLogin_returnsFalseWithErrorMessage() {
        val (isValid, errorMessage) = fragment.validateInputs("login@", "username", "password", "password") // Недопустимый символ в логине

        assertEquals(false, isValid)
        assertEquals("Ошибка: Недопустимые символы в первой строке", errorMessage)
    }

    @Test
    fun validateInputs_invalidCharactersInUsername_returnsFalseWithErrorMessage() {
        val (isValid, errorMessage) =
            fragment.validateInputs(
                "login",
                "user@name",
                "password",
                "password",
            ) // Недопустимый символ в имени пользователя

        assertEquals(false, isValid)
        assertEquals("Ошибка: Недопустимые символы во второй строке", errorMessage)
    }

    @Test
    fun validateInputs_invalidCharactersInPassword_returnsFalseWithErrorMessage() {
        val (isValid, errorMessage) = fragment.validateInputs("login", "username", "pass@word", "pass@word") // Недопустимый символ в пароле

        assertEquals(false, isValid)
        assertEquals("Ошибка: Недопустимые символы в третьей строке", errorMessage)
    }
}
