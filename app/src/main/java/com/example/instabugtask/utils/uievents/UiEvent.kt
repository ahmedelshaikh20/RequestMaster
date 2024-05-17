package com.example.instabugtask.utils.uievents

sealed class UiEvent {

  data class ShowToast(val message: UiText) : UiEvent()

}
