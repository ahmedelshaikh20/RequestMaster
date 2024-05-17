package com.example.instabugtask.viewmodel

import com.example.instabugtask.FakeRepository
import com.example.instabugtask.ui.screens.logscreen.LogScreenEvents
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before



class LogsViewModelTest {
private lateinit var fakeViewModel: LogsViewModel

  @Before
  fun setUp() {
    fakeViewModel = LogsViewModel(FakeRepository())

  }


  @Test
  fun `onEvent should update isGetChecked Correctly`() {
    fakeViewModel.onEvent(LogScreenEvents.OnGetClick)
    assertEquals(true, fakeViewModel.state.isGetChecked)
  }

  @Test
  fun `onEvent should update isPostChecked Correctly`() {
    fakeViewModel.onEvent(LogScreenEvents.OnPostClick)
    assertEquals(true, fakeViewModel.state.isPostChecked)
  }

  @Test
  fun `onEvent should update isSuccessChecked Correctly`() {
    fakeViewModel.onEvent(LogScreenEvents.OnSuccessClick)
    assertEquals(true, fakeViewModel.state.isSuccessChecked)
  }

  @Test
  fun `onEvent should update isFailureChecked Correctly`() {
    fakeViewModel.onEvent(LogScreenEvents.OnFailClickClick)
    assertEquals(true, fakeViewModel.state.isFailureChecked)
  }

  @Test
  fun `onEvent should update isAscendingClicked Correctly`() {
    fakeViewModel.onEvent(LogScreenEvents.OnAscendingOrderClick)
    assertEquals(true, fakeViewModel.state.isAscendingClicked)
  }

  @Test
  fun `nEvent should update isDescendingClicked Correctly`() {
    fakeViewModel.onEvent(LogScreenEvents.OnDescendingOrderClick)
    assertEquals(true, fakeViewModel.state.isDescendingClicked)
  }


}
