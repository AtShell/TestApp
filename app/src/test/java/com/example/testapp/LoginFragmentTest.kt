package com.example.testapp

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LoginFragmentTest {
    private lateinit var fragment: LoginFragment

    @Before
    fun setUp() {
        fragment = LoginFragment()
    }

    @Test
    fun validateInputs_emptyLogin_returnsFalseWithErrorMessage() {
        val (isValid, errorMessage) = fragment.validateInputs("", "password") // Пустой логин

        assertEquals(false, isValid)
        assertEquals("Ошибка: Пустая строка логина", errorMessage)
    }

    @Test
    fun validateInputs_emptyPassword_returnsFalseWithErrorMessage() {
        val (isValid, errorMessage) = fragment.validateInputs("login", "") // Пустой пароль

        assertEquals(false, isValid)
        assertEquals("Ошибка: Пустая строка пароля", errorMessage)
    }

    @Test
    fun validateInputs_validInputs_returnsTrueWithEmptyErrorMessage() {
        val (isValid, errorMessage) = fragment.validateInputs("login", "password") // Корректные данные

        assertEquals(true, isValid)
        assertEquals("", errorMessage) // Ошибки быть не должно
    }
}
