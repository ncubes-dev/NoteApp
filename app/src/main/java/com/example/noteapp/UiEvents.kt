package com.example.noteapp

sealed class UiEvents{
   data class Navigate(var route:String):UiEvents()
   data class ShowSnackbar(var message:String, val actionLabel:String?=null):UiEvents()
   object ClearTextField:UiEvents()
}

