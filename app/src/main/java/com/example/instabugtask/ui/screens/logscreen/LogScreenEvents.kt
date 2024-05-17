package com.example.instabugtask.ui.screens.logscreen

sealed class LogScreenEvents {

  data object OnGetClick:LogScreenEvents()
  data object OnPostClick:LogScreenEvents()
  data object OnSuccessClick:LogScreenEvents()
  data object OnFailClickClick:LogScreenEvents()
  data object OnRunClick:LogScreenEvents()
  data object OnAscendingOrderClick:LogScreenEvents()
  data object OnDescendingOrderClick:LogScreenEvents()

}
