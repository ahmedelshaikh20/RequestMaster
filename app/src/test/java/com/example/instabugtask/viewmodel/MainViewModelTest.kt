package com.example.instabugtask.viewmodel

import com.example.instabugtask.FakeRepository
import com.example.instabugtask.ui.screens.main.MainScreenUiEvents
import com.example.instabugtask.ui.screens.main.model.RequestType
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import java.util.concurrent.ExecutorService

class MainViewModelTest {
  private lateinit var fakeViewModel: MainViewModel
  @Mock
  private lateinit var mockExecutorService: ExecutorService
  @Before
  fun setUp() {
    fakeViewModel = MainViewModel(FakeRepository())

  }


  @Test
  fun `onEvent should update header key in state correctly for HeaderKeyChanged event`() {
    val newHeaderKey = "Authorization"
    val event = MainScreenUiEvents.HeaderKeyChanged(newHeaderKey)
    fakeViewModel.onEvent(event)
    assertEquals(newHeaderKey, fakeViewModel.state.currentKey)
  }

  @Test
  fun `onEvent should update header Value in state correctly for HeaderValueChanged event`() {
    val newHeaderValue = "Lol"
    val event = MainScreenUiEvents.HeaderValueChanged(newHeaderValue)
    fakeViewModel.onEvent(event)
    assertEquals(newHeaderValue, fakeViewModel.state.currentHeader)
  }

  @Test
  fun `onEvent should update url Value in state correctly for UrlChanged event`() {
    val newUrl = "Lol"
    val event = MainScreenUiEvents.UrlChanged(newUrl)
    fakeViewModel.onEvent(event)
    assertEquals(newUrl, fakeViewModel.state.url)
  }

  @Test
  fun `onEvent should update request type Value in state correctly for RequestType Changed event`() {
    val newRequestType = RequestType.GetRequest
    val event = MainScreenUiEvents.RequestTypeChanged(newRequestType.request)
    fakeViewModel.onEvent(event)
    assertEquals(newRequestType.request, fakeViewModel.state.requestType.request)
  }

  @Test
  fun `onEvent should update File Path Value in state correctly for Chosen File Changed event`() {
    val newFileName = "/lol/lol/lol"
    val event = MainScreenUiEvents.FileNameChanged(newFileName)
    fakeViewModel.onEvent(event)
    assertEquals(newFileName, fakeViewModel.state.filePath)
  }

  @Test
  fun `onEvent should update Query Parameter Value in state correctly for Query Parameter Changed event`() {
    val newQueryParameter = "fake_param"
    val event = MainScreenUiEvents.QueryParameterChanged(newQueryParameter)
    fakeViewModel.onEvent(event)
    assertEquals(newQueryParameter, fakeViewModel.state.queryParameter)
  }

  @Test
  fun `onEvent should update Query Value in state correctly for Query Value Changed event`() {
    val newQueryValue = "fake_value"
    val event = MainScreenUiEvents.QueryValueChanged(newQueryValue)
    fakeViewModel.onEvent(event)
    assertEquals(newQueryValue, fakeViewModel.state.queryValue)
  }

  @Test
  fun `onEvent should update currentJsonKey in state correctly for JsonKeyChange event`() {
    val newJsonKey = "fake_json_key"
    val event = MainScreenUiEvents.JsonKeyChange(newJsonKey)
    fakeViewModel.onEvent(event)
    assertEquals(newJsonKey, fakeViewModel.state.currentJsonKey)
  }

  @Test
  fun `onEvent should update currentJsonValue in state correctly for JsonValueChanged event`() {
    val newJsonValue = "fake_json_value"
    val event = MainScreenUiEvents.JsonValueChanged(newJsonValue)
    fakeViewModel.onEvent(event)
    assertEquals(newJsonValue, fakeViewModel.state.currentJsonValue)
  }

  @Test
  fun `onEvent should update headers in state correctly for onAddHeaders event`() {
    val newHeaderKey = "Auth"
    val newHeaderValue = "Lol"


    fakeViewModel.onEvent(MainScreenUiEvents.HeaderValueChanged(newHeaderValue))
    fakeViewModel.onEvent(MainScreenUiEvents.HeaderKeyChanged(newHeaderKey))
    fakeViewModel.onEvent(MainScreenUiEvents.OnAddHeaderClick)

    val newHeaders = mapOf(newHeaderKey to newHeaderValue)
    assertEquals(newHeaders, fakeViewModel.state.headers)
  }
  @Test
  fun `onEvent should update queries in state correctly for OnAddQueryClick event`() {
    val newQueryParameter = "page"
    val newQueryValue = "1"

    fakeViewModel.onEvent(MainScreenUiEvents.QueryParameterChanged(newQueryParameter))
    fakeViewModel.onEvent(MainScreenUiEvents.QueryValueChanged(newQueryValue))
    fakeViewModel.onEvent(MainScreenUiEvents.OnAddQueryClick)

    val expectedQueries = mapOf(newQueryParameter to newQueryValue)

    assertEquals(expectedQueries, fakeViewModel.state.querys)
  }

  @Test
  fun `onEvent should clear queries correctly for OnRequestTypeChanged event`() {
    val newQueryParameter = "page"
    val newQueryValue = "1"

    fakeViewModel.onEvent(MainScreenUiEvents.QueryParameterChanged(newQueryParameter))
    fakeViewModel.onEvent(MainScreenUiEvents.QueryValueChanged(newQueryValue))
    fakeViewModel.onEvent(MainScreenUiEvents.RequestTypeChanged(RequestType.PostRequest.request))


    val expectedQueries = emptyMap<String , String>()

    assertEquals(expectedQueries, fakeViewModel.state.querys)
  }
  @Test
  fun `onEvent should clear headers correctly for OnRequestTypeChanged event`() {
    val newHeaderKey = "Auth"
    val newHeaderValue = "Lol"

    fakeViewModel.onEvent(MainScreenUiEvents.HeaderValueChanged(newHeaderValue))
    fakeViewModel.onEvent(MainScreenUiEvents.HeaderKeyChanged(newHeaderKey))
    fakeViewModel.onEvent(MainScreenUiEvents.OnAddHeaderClick)


    val expectedHeaders = emptyMap<String , String>()

    assertEquals(expectedHeaders, fakeViewModel.state.querys)
  }

}
