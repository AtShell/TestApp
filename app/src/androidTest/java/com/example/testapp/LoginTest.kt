package com.example.testapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginTest {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testSuccessfulLogin() {
        onView(withId(R.id.login)).perform(typeText("testLogin"), closeSoftKeyboard())

        onView(withId(R.id.password)).perform(typeText("testPassword"), closeSoftKeyboard())

        onView(withId(R.id.loginButton)).perform(click())

        // Проверка, что произошел переход на SuccessFragment
        onView(withId(R.id.dynamicTextView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testFailedLogin() {
        // Ввод неправильных данных
        onView(withId(R.id.login)).perform(typeText("wrongLogin"), closeSoftKeyboard())
        onView(withId(R.id.password)).perform(typeText("wrongPassword"), closeSoftKeyboard())

        onView(withId(R.id.loginButton)).perform(click())

        // Проверка, что появилось сообщение об ошибке
        onView(withId(R.id.errorTextView)).check(matches(isDisplayed()))
    }
}
