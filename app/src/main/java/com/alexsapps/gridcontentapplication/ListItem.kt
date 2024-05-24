package com.alexsapps.gridcontentapplication


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

data class ListItem(

    var text: String = "",
    var color: MutableState<Color> = mutableStateOf(Color.Green),
    var stateFullSize: MutableState<Int> = mutableIntStateOf(1),
    private var isAscending: MutableState<Boolean> = mutableStateOf(false)

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




}

