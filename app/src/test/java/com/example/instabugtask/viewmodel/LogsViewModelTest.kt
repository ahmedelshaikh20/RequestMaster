package com.example.instabugtask.viewmodel

import android.os.Handler
import com.example.instabugtask.FakeRepository
import com.example.instabugtask.ui.screens.logscreen.LogScreenEvents
import io.mockk.mockk
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import java.util.concurrent.ExecutorService



class LogsViewModelTest {
private lateinit var viewModel: LogsViewModel

  @Before
  fun setUp() {
    viewModel = LogsViewModel(FakeRepository())

  }



  @Test
  fun `onEvent should update isGetChecked Correctly`() {
    viewModel.onEvent(LogScreenEvents.OnGetClick)
    assertEquals(true, viewModel.state.isGetChecked)
  }

  @Test
  fun `onEvent should update isPostChecked Correctly`() {
    viewModel.onEvent(LogScreenEvents.OnPostClick)
    assertEquals(true, viewModel.state.isPostChecked)
  }

  @Test
  fun `onEvent should update isSuccessChecked Correctly`() {
    viewModel.onEvent(LogScreenEvents.OnSuccessClick)
    assertEquals(true, viewModel.state.isSuccessChecked)
  }

  @Test
  fun `onEvent should update isFailureChecked Correctly`() {
    viewModel.onEvent(LogScreenEvents.OnFailClickClick)
    assertEquals(true, viewModel.state.isFailureChecked)
  }

  @Test
  fun `onEvent should update isAscendingClicked Correctly`() {
    viewModel.onEvent(LogScreenEvents.OnAscendingOrderClick)
    assertEquals(true, viewModel.state.isAscendingClicked)
  }

  @Test
  fun `nEvent should update isDescendingClicked Correctly`() {
    viewModel.onEvent(LogScreenEvents.OnDescendingOrderClick)
    assertEquals(true, viewModel.state.isDescendingClicked)
  }


}
