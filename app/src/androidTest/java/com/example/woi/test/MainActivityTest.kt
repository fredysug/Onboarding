package com.example.woi.test

import android.content.Intent
import android.os.SystemClock
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.PositionAssertions.isCompletelyBelow
import androidx.test.espresso.assertion.PositionAssertions.isCompletelyRightOf
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val rule = IntentsTestRule<MainActivity>(MainActivity::class.java, false, false)

    lateinit var intent: Intent

    val receiverName = "Ann"
    val saldo = 1000L

    @Before
    fun setup() {
        intent = Intent().putExtra("receiverName", receiverName).putExtra("Saldo", saldo)
        rule.launchActivity(intent)
    }

    @Test
    fun testShowHelloWorld() {
        onView(withText("Hello world!")).check(matches(isDisplayed()))
    }

    @Test
    fun testShowReceiverName() {
        val penerimaMathcer = withText("Penerima:")
        val receiverTextMatcher = onView(withText(receiverName))
        receiverTextMatcher.check(isCompletelyBelow(penerimaMathcer)).check(matches(isDisplayed()))
        receiverTextMatcher.check(isCompletelyRightOf(withId(R.id.receiverAvatar))).check(matches(isDisplayed()))
        onView(penerimaMathcer).check(isCompletelyRightOf(withId(R.id.receiverAvatar))).check(matches(isDisplayed()))
    }

    @Test
    fun testToolbarTitle() {
        onView(withText("Kirim DANA")).check(matches(isDisplayed()))
    }

    @Test
    fun testInputNominal() {
        //test input
        onView(withId(R.id.inputAmount)).perform(replaceText(""))
        onView(withId(R.id.clearIcon)).check(matches(not(isDisplayed())))

        onView(withId(R.id.inputAmount)).perform(typeText("123"))
        onView(withId(R.id.inputAmount)).check(matches(withText("123")))
        onView(withId(R.id.clearIcon)).check(matches(isDisplayed()))

        onView(withId(R.id.inputAmount)).perform(typeText("abc"))
        onView(withId(R.id.inputAmount)).check(matches(withText("123")))

        onView(withId(R.id.clearIcon)).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.inputAmount)).check(matches(withText("")))

        //test saldo/balance message when input

        onView(withId(R.id.message)).check(matches(withText("Saldo DANA Anda: $saldo")))

        SystemClock.sleep(3000)
        onView(withId(R.id.inputAmount)).perform(typeText("100"))

        onView(withId(R.id.message)).check(matches(withText("Saldo DANA Anda: $saldo")))
        SystemClock.sleep(3000)
        onView(withId(R.id.inputAmount)).perform(typeText("1"))
        onView(withId(R.id.message)).check(matches(withText("Saldo DANA Anda tidak mencukupi!")))
    }
}