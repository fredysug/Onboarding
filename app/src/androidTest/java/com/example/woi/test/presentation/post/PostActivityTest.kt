package com.example.woi.test.presentation.post

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.woi.test.R
import com.example.woi.test.utils.Post
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PostActivityTest {

    @get:Rule
    val rule = IntentsTestRule<PostActivity>(PostActivity::class.java, false , false)
    lateinit var intent: Intent

    lateinit var view: PostView
    val userId = "1"

    @Before
    fun setup() {
        intent = Intent().putExtra("userId", userId)
        rule.launchActivity(intent)
        view = rule.activity as PostView
    }

    @Test
    fun testGetPostData() {
        val generatedPosts = (1..5).map { Post(
                userId = userId.toInt(),
                id = it,
                title = "title-$it",
                body = "body-$it")
        }

        val expectedPosition = 2
        val expectedPost = generatedPosts[expectedPosition]

        rule.runOnUiThread {
            view.showPost(generatedPosts)
        }

        onView(withId(R.id.rvPost)).check(matches(isPostTextShown(expectedPosition, expectedPost.id.toString(), expectedPost.title, expectedPost.body)))
    }

    private fun isPostTextShown(position: Int, id: String, title: String, body: String) =
        object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description?) {
                description?.appendText("Error")
            }

            override fun matchesSafely(item: View?): Boolean {
                if (item != null && item is RecyclerView) {
                    val adapter = item.adapter as PostAdapter
                    return adapter.posts.isNotEmpty()
                            && adapter.posts[position].id.toString() == id
                            && adapter.posts[position].title == title
                            && adapter.posts[position].body == body
                } else {
                    return false
                }
            }
        }
}