package com.example.instabugtask.uitesting

import android.content.Context
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import com.example.instabugtask.MainActivity
import com.example.instabugtask.R
import com.example.instabugtask.ui.screens.main.MainScreenUiEvents
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class EndToEndTesting {

  @get:Rule
  val composeTestRule = createAndroidComposeRule<MainActivity>()


  @Test
  fun EndToEndGetRequestTest() {
    val context = ApplicationProvider.getApplicationContext<Context>()

    // Enter the Url
    composeTestRule.onNodeWithText(context.getString(R.string.enter_url_here)).performClick()
    composeTestRule.onNodeWithText(context.getString(R.string.enter_url_here))
      .performTextInput("https://httpbin.org/get")
    // Add Headers
    composeTestRule.onNodeWithText(context.getString(R.string.header_key_label))
      .performTextInput("newHeaderKey")
    composeTestRule.onNodeWithText(context.getString(R.string.header_value_label))
      .performTextInput("newHeaderValue")
    composeTestRule.onNodeWithText(context.getString(R.string.add_header_button)).performClick()
    //Add Query Parameter
    composeTestRule.onNodeWithText(context.getString(R.string.add_parameters_button)).performClick()
    composeTestRule.onNodeWithText(context.getString(R.string.query_parameter_label))
      .performTextInput("user")
    composeTestRule.onNodeWithText(context.getString(R.string.query_value_label))
      .performTextInput("ahmed")
    composeTestRule.onNodeWithText(context.getString(R.string.add_query_button)).performClick()
    composeTestRule.onNodeWithText(context.getString(R.string.close_button)).performClick()
    //Add on Execute Click
    composeTestRule.onNodeWithText(context.getString(R.string.execute_button)).performClick()
    Thread.sleep(4000) // Sleep for 1 second

    //onView(withContentDescription(context.getString(R.string.request_result))).perform(click())
    val expectedResTest = "https://httpbin.org/get?user=ahmed&"
    composeTestRule.onNodeWithTag(context.getString(R.string.request_result))
      .assertTextContains(expectedResTest)

  }

  @Test
  fun EndToEndPostRequestTest() {
    val context = ApplicationProvider.getApplicationContext<Context>()

    composeTestRule.onNodeWithText(context.getString(R.string.clear_button)).performClick()

    // Enter the Url
    composeTestRule.onNodeWithText(context.getString(R.string.enter_url_here)).performClick()
    composeTestRule.onNodeWithText(context.getString(R.string.enter_url_here))
      .performTextInput("https://httpbin.org/post")
    //Choose Post With Json Body
    composeTestRule.onNodeWithText(context.getString(R.string.post_request)).performClick()

    // Add Headers
    composeTestRule.onNodeWithText(context.getString(R.string.header_key_label))
      .performTextInput("newHeaderKey")
    composeTestRule.onNodeWithText(context.getString(R.string.header_value_label))
      .performTextInput("newHeaderValue")
    composeTestRule.onNodeWithText(context.getString(R.string.add_header_button)).performClick()
    //Add Json Body Key Value
    composeTestRule.onNodeWithText(context.getString(R.string.add_json_body)).performClick()
    composeTestRule.onNodeWithText(context.getString(R.string.json_key_label))
      .performTextInput("q")
    composeTestRule.onNodeWithText(context.getString(R.string.json_value_label))
      .performTextInput("ahmed")
    composeTestRule.onNodeWithText(context.getString(R.string.add_json)).performClick()
    composeTestRule.onNodeWithText(context.getString(R.string.close_button)).performClick()
    //Add on Execute Click
    composeTestRule.onNodeWithText(context.getString(R.string.execute_button)).performClick()
    Thread.sleep(4000) // Sleep for 1 second

    //onView(withContentDescription(context.getString(R.string.request_result))).perform(click())
    val expectedResTest = "https://httpbin.org/post"
    composeTestRule.onNodeWithTag(context.getString(R.string.request_result))
      .assertTextContains(expectedResTest)

  }

  @Test
  fun EndToEndPostRequestWithUplaodFileTest() {
    val context = ApplicationProvider.getApplicationContext<Context>()

    composeTestRule.onNodeWithText(context.getString(R.string.clear_button)).performClick()

    // Enter the Url
    composeTestRule.onNodeWithText(context.getString(R.string.enter_url_here)).performClick()
    composeTestRule.onNodeWithText(context.getString(R.string.enter_url_here))
      .performTextInput("https://httpbin.org/post")
    //Choose Post With File Body
    composeTestRule.onNodeWithText(context.getString(R.string.post_with_file_request)).performClick()

    // Add Headers
    composeTestRule.onNodeWithText(context.getString(R.string.header_key_label))
      .performTextInput("newHeaderKey")
    composeTestRule.onNodeWithText(context.getString(R.string.header_value_label))
      .performTextInput("newHeaderValue")
    composeTestRule.onNodeWithText(context.getString(R.string.add_header_button)).performClick()
    // Add File
    //composeTestRule.onNodeWithText(context.getString(R.string.add_file)).performClick()
    composeTestRule.activity.mainViewModel.onEvent(MainScreenUiEvents.FileNameChanged("/storage/emulated/0/Download/lol.txt"))

    //Add on Execute Click
    composeTestRule.onNodeWithText(context.getString(R.string.execute_button)).performClick()
    Thread.sleep(4000)

    //onView(withContentDescription(context.getString(R.string.request_result))).perform(click())
//    val expectedResTest = "https://httpbin.org/post"
//    composeTestRule.onNodeWithTag(context.getString(R.string.request_result)).performClick()
    assertEquals("ERROR : Select File to Proceed", composeTestRule.activity.mainViewModel.state.currentToastMessage)

  }



}
