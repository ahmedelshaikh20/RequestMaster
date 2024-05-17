package com.example.instabugtask.ui.screens.logscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.instabugtask.ui.screens.components.ActionButton
import com.example.instabugtask.ui.screens.components.BoldTextField
import com.example.instabugtask.ui.screens.components.ExpandableCard
import com.example.instabugtask.ui.screens.components.OptionComponent
import com.example.instabugtask.utils.dimensions.LocalSpacing
import com.example.instabugtask.viewmodel.LogsViewModel


@Composable
fun LogScreen(viewModel: LogsViewModel, navController: NavHostController) {
  LaunchedEffect(key1 = true) {
    viewModel.getAllLogs()
  }
  val state = viewModel.state
  Column(modifier = Modifier.padding(10.dp)) {
    LazyVerticalGrid(
      columns = GridCells.Fixed(2),
      verticalArrangement = Arrangement.spacedBy(10.dp),
      horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
      items(6) { index ->
        when (index) {
          0 -> OptionComponent(
            value = "Ascending Order",
            onCheckClicked = { viewModel.onEvent(LogScreenEvents.OnAscendingOrderClick) },
            checkedState = state.isAscendingClicked
          )
          1 -> OptionComponent(
            value = "Descending Order",
            onCheckClicked = { viewModel.onEvent(LogScreenEvents.OnDescendingOrderClick) },
            checkedState = state.isDescendingClicked
          )
          2 -> OptionComponent(
            value = "GET",
            onCheckClicked = { viewModel.onEvent(LogScreenEvents.OnGetClick) },
            checkedState = state.isGetChecked
          )
          3 -> OptionComponent(
            value = "POST",
            onCheckClicked = { viewModel.onEvent(LogScreenEvents.OnPostClick) },
            checkedState = state.isPostChecked
          )
          4 -> OptionComponent(
            value = "Success",
            onCheckClicked = { viewModel.onEvent(LogScreenEvents.OnSuccessClick) },
            checkedState = state.isSuccessChecked
          )
          5 -> OptionComponent(
            value = "Failure",
            onCheckClicked = { viewModel.onEvent(LogScreenEvents.OnFailClickClick) },
            checkedState = state.isFailureChecked
          )
        }
      }
    }
    ActionButton(
      modifier = Modifier.padding(10.dp),
      text = "Run",
      onClick = { viewModel.onEvent(LogScreenEvents.OnRunClick) })
    LazyColumn(
      modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(
        LocalSpacing.current.spaceSmall
      )
    ) {
      items(state.currentLogs) {
        ExpandableCard(responseInfo = it)
        Divider()
      }
    }


  }


}
