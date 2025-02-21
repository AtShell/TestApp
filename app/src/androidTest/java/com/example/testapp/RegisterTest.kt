package com.example.testapp
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterTest {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testSuccessfulRegister() {
        // Нажимаем на "Зарегистрироваться" на экране авторизации
        onView(withId(R.id.signupText)).perform(click())

        onView(withId(R.id.login)).perform(typeText("newLogin"), closeSoftKeyboard())

        onView(withId(R.id.username)).perform(typeText("newUsername"), closeSoftKeyboard())

        onView(withId(R.id.password)).perform(typeText("newPassword"), closeSoftKeyboard())

        onView(withId(R.id.password_repeat)).perform(typeText("newPassword"), closeSoftKeyboard())

        onView(withId(R.id.registerButton)).perform(click())

        // Проверка, что произошел переход на LoginFragment
        onView(withId(R.id.loginButton)).check(matches(isDisplayed()))
    }

    @Test
    fun testFailedRegister() {
        // Нажимаем на "Зарегистрироваться" на экране авторизации
        onView(withId(R.id.signupText)).perform(click())

        onView(withId(R.id.login)).perform(typeText("newUserLogin"), closeSoftKeyboard())

        onView(withId(R.id.username)).perform(typeText("NewUsername"), closeSoftKeyboard())

        onView(withId(R.id.password)).perform(typeText("password1"), closeSoftKeyboard())
        onView(withId(R.id.password_repeat)).perform(typeText("password2"), closeSoftKeyboard())

        onView(withId(R.id.registerButton)).perform(click())

        // Проверка, что сообщение об ошибке отобразилось
        onView(withId(R.id.errorTextView)).check(matches(isDisplayed()))
    }
}
