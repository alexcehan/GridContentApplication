package com.alexsapps.gridcontentapplication

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

data class ListItem(

    var text: String = "",
    var color: MutableState<Color> = mutableStateOf(Color.Green),
    //val stateFullSize: MutableState<Int> = mutableStateOf(1),

    var stateFullSize: MutableState<Int> = mutableStateOf(1),
    val isBoxFilled: MutableState<Boolean> = mutableStateOf(false),
    private var isAscending: MutableState<Boolean> = mutableStateOf(false),


) {

    fun onClick() {
        if (this.stateFullSize.value == 1) {
            this.stateFullSize.value = 2
            this.isAscending.value = true


        } else if (this.stateFullSize.value == 2) {
            if (this.isAscending.value) {
                this.stateFullSize.value = 3
                this.isAscending.value = false

            } else {
                this.stateFullSize.value = 1
                this.isAscending.value = true
            }

        } else {
            this.stateFullSize.value = 2
            this.isAscending.value = false

        }
    }

//    fun onClick(): ListItem {
//        if (this.stateFullSize.value) {
//            return this.copy(stateFullSize = mutableStateOf(false))
//        } else {
//            return this.copy(stateFullSize = mutableStateOf(true))
//        }
//    }

//    fun onClick() {
//        this.stateFullSize.value = !this.stateFullSize.value
//    }
    override fun toString(): String {
        return this.text +"- " +this.hashCode()
    }
}

