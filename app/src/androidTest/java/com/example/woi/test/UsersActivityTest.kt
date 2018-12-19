package com.example.woi.test

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.woi.test.presentation.post.PostActivity
import com.example.woi.test.presentation.users.UserAdapter
import com.example.woi.test.presentation.users.UsersActivity
import com.example.woi.test.utils.User
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UsersActivityTest {

    @get:Rule
    val rule = IntentsTestRule<UsersActivity>(UsersActivity::class.java, false, false)

    @Before
    fun setup() {
        rule.launchActivity(null)
    }

    @Test
    fun testAllUserShown() {
        val view = rule.activity as com.example.woi.test.presentation.users.UserView
        onView(withId(R.id.rvUsers)).check(matches(not(itemNotEmptyMatcher())))

        rule.runOnUiThread {
            view.showAllUser(listOf(User(username = "User 3"), User(username = "User 4")))
        }
        onView(withId(R.id.rvUsers)).check(matches(itemNotEmptyMatcher()))
        onView(withId(R.id.rvUsers)).check(matches(isItemTextShown(1, "User 4")))
    }

    @Test
    fun testErrorShown() {
        val errorMessage = "Random Error message"

        val view = rule.activity as com.example.woi.test.presentation.users.UserView

        rule.runOnUiThread {
            view.showErrorMessage(errorMessage)
        }

        onView(withText("Error")).inRoot(isDialog()).check(matches(isDisplayed()))
        onView(withText(errorMessage)).inRoot(isDialog()).check(matches(isDisplayed()))
        onView(withText("OK")).inRoot(isDialog()).check(matches(isDisplayed()))
    }

    @Test
    fun testUserClicked() {
        val view = rule.activity as com.example.woi.test.presentation.users.UserView
        val userId = "1"

        rule.runOnUiThread {
            view.openPost(userId)
        }

        intended(
            allOf(
                hasComponent(PostActivity::class.java.canonicalName),
                hasExtra("userId", userId)
            )
        )
    }

    private fun itemNotEmptyMatcher() = object : TypeSafeMatcher<View>() {
        var errorMessage: String = ""

        override fun describeTo(description: Description?) {
            description?.appendText(errorMessage)
        }

        override fun matchesSafely(item: View?): Boolean {
            if (item != null && item is RecyclerView) {
                if (item.adapter?.itemCount != 0) {
                    errorMessage = "Item not empty"
                } else {
                    errorMessage = "item empty"
                }
                return item.adapter?.itemCount != 0
            } else {
                errorMessage = "UserView is not recyclerView"
                return false
            }
        }
    }

    private fun isItemTextShown(position: Int, textMatcher: String) = object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description?) {
            description?.appendText("Errore")
        }

        override fun matchesSafely(item: View?): Boolean {
            if (item != null && item is RecyclerView) {
                val adapter = item.adapter as UserAdapter
                return adapter.users.isNotEmpty() && adapter.users[position].username == textMatcher
            } else {
                return false
            }
        }
    }

}