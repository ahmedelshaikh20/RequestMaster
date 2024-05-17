package com.example.instabugtask.viewmodel

import com.example.instabugtask.FakeRepository
import com.example.instabugtask.domain.model.ResponseInfo
import com.example.instabugtask.ui.screens.main.MainScreenUiEvents
import com.example.instabugtask.ui.screens.main.model.RequestType
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class MainViewModelTest {
  private lateinit var viewModel: MainViewModel


  @Before
  fun setUp() {
    viewModel = MainViewModel(FakeRepository())

  }


  @Test
  fun `onEvent should update header key in state correctly for HeaderKeyChanged event`() {
    val newHeaderKey = "Authorization"
    val event = MainScreenUiEvents.HeaderKeyChanged(newHeaderKey)
    viewModel.onEvent(event)
    assertEquals(newHeaderKey, viewModel.state.currentKey)
  }

  @Test
  fun `onEvent should update header Value in state correctly for HeaderValueChanged event`() {
    val newHeaderValue = "Lol"
    val event = MainScreenUiEvents.HeaderValueChanged(newHeaderValue)
    viewModel.onEvent(event)
    assertEquals(newHeaderValue, viewModel.state.currentHeader)
  }

  @Test
  fun `onEvent should update url Value in state correctly for UrlChanged event`() {
    val newUrl = "Lol"
    val event = MainScreenUiEvents.UrlChanged(newUrl)
    viewModel.onEvent(event)
    assertEquals(newUrl, viewModel.state.url)
  }

  @Test
  fun `onEvent should update request type Value in state correctly for RequestType Changed event`() {
    val newRequestType = RequestType.GetRequest
    val event = MainScreenUiEvents.RequestTypeChanged(newRequestType.request)
    viewModel.onEvent(event)
    assertEquals(newRequestType.request, viewModel.state.requestType.request)
  }

  @Test
  fun `onEvent should update File Path Value in state correctly for Chosen File Changed event`() {
    val newFileName = "/lol/lol/lol"
    val event = MainScreenUiEvents.FileNameChanged(newFileName)
    viewModel.onEvent(event)
    assertEquals(newFileName, viewModel.state.filePath)
  }

  @Test
  fun `onEvent should update Query Parameter Value in state correctly for Query Parameter Changed event`() {
    val newQueryParameter = "fake_param"
    val event = MainScreenUiEvents.QueryParameterChanged(newQueryParameter)
    viewModel.onEvent(event)
    assertEquals(newQueryParameter, viewModel.state.queryParameter)
  }

  @Test
  fun `onEvent should update Query Value in state correctly for Query Value Changed event`() {
    val newQueryValue = "fake_value"
    val event = MainScreenUiEvents.QueryValueChanged(newQueryValue)
    viewModel.onEvent(event)
    assertEquals(newQueryValue, viewModel.state.queryValue)
  }

  @Test
  fun `onEvent should update currentJsonKey in state correctly for JsonKeyChange event`() {
    val newJsonKey = "fake_json_key"
    val event = MainScreenUiEvents.JsonKeyChange(newJsonKey)
    viewModel.onEvent(event)
    assertEquals(newJsonKey, viewModel.state.currentJsonKey)
  }

  @Test
  fun `onEvent should update currentJsonValue in state correctly for JsonValueChanged event`() {
    val newJsonValue = "fake_json_value"
    val event = MainScreenUiEvents.JsonValueChanged(newJsonValue)
    viewModel.onEvent(event)
    assertEquals(newJsonValue, viewModel.state.currentJsonValue)
  }

  @Test
  fun `onEvent should update headers in state correctly for onAddHeaders event`() {
    val newHeaderKey = "Auth"
    val newHeaderValue = "Lol"


    viewModel.onEvent(MainScreenUiEvents.HeaderValueChanged(newHeaderValue))
    viewModel.onEvent(MainScreenUiEvents.HeaderKeyChanged(newHeaderKey))
    viewModel.onEvent(MainScreenUiEvents.OnAddHeaderClick)

    val newHeaders = mapOf(newHeaderKey to newHeaderValue)
    assertEquals(newHeaders, viewModel.state.headers)
  }

  @Test
  fun `onEvent should update queries in state correctly for OnAddQueryClick event`() {
    val newQueryParameter = "page"
    val newQueryValue = "1"

    viewModel.onEvent(MainScreenUiEvents.QueryParameterChanged(newQueryParameter))
    viewModel.onEvent(MainScreenUiEvents.QueryValueChanged(newQueryValue))
    viewModel.onEvent(MainScreenUiEvents.OnAddQueryClick)

    val expectedQueries = mapOf(newQueryParameter to newQueryValue)

    assertEquals(expectedQueries, viewModel.state.queries)
  }

  @Test
  fun `onEvent should clear queries correctly for OnRequestTypeChanged event`() {
    val newQueryParameter = "page"
    val newQueryValue = "1"

    viewModel.onEvent(MainScreenUiEvents.QueryParameterChanged(newQueryParameter))
    viewModel.onEvent(MainScreenUiEvents.QueryValueChanged(newQueryValue))
    viewModel.onEvent(MainScreenUiEvents.RequestTypeChanged(RequestType.PostRequest.request))


    val expectedQueries = emptyMap<String, String>()

    assertEquals(expectedQueries, viewModel.state.queries)
  }

  @Test
  fun `onEvent should clear headers correctly for OnRequestTypeChanged event`() {
    val newHeaderKey = "Auth"
    val newHeaderValue = "Lol"

    viewModel.onEvent(MainScreenUiEvents.HeaderValueChanged(newHeaderValue))
    viewModel.onEvent(MainScreenUiEvents.HeaderKeyChanged(newHeaderKey))
    viewModel.onEvent(MainScreenUiEvents.OnAddHeaderClick)


    val expectedHeaders = emptyMap<String, String>()

    assertEquals(expectedHeaders, viewModel.state.queries)
  }

//  @Test
//  fun `onEvent should return Response Info correctly for onExecute Get Event event`() {
//    val url = "https://httpbin.com/post"
//    val newHeaderKey = "Auth"
//    val newHeaderValue = "Lol"
//    val newQueryParameter = "page"
//    val newQueryValue = "1"
//    viewModel.onEvent(MainScreenUiEvents.UrlChanged(url))
//    viewModel.onEvent(MainScreenUiEvents.HeaderValueChanged(newHeaderValue))
//    viewModel.onEvent(MainScreenUiEvents.HeaderKeyChanged(newHeaderKey))
//    viewModel.onEvent(MainScreenUiEvents.OnAddHeaderClick)
//
//    viewModel.onEvent(MainScreenUiEvents.RequestTypeChanged(RequestType.GetRequest.request))
//    viewModel.onEvent(MainScreenUiEvents.OnExecuteClick)
//
//    // Add Query
//    viewModel.onEvent(MainScreenUiEvents.QueryParameterChanged(newQueryParameter))
//    viewModel.onEvent(MainScreenUiEvents.QueryValueChanged(newQueryValue))
//    val expectedResult = ResponseInfo(
//      requestBody = "",
//      responseStatusCode = 200,
//      responseBody = "Mock Response",
//      requestType = "GET",
//      executionTime = "100 ms",
//      requestUrl = url,
//      requestHeaders = mapOf(newHeaderKey to newHeaderValue).toString(),
//      error = "",
//      queryParameters = mapOf(newQueryParameter to newQueryValue).toString(),
//      responseHeaders = "",
//      responseStatus = "Success",
//      filePath = "/lol/lol.txt"
//    )
//
//    assertEquals(expectedResult, viewModel.state.currentRequest)
//  }

}
