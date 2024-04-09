package com.alexsapps.gridcontentapplication

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch

class GridViewModel(val listOfItemsVal: MutableStateFlow<MutableList<ListItem>>): ViewModel() {

    var _listOfItems: SnapshotStateList<ListItem> = mutableStateListOf()


    init {
        viewModelScope.launch {
            listOfItemsVal.collect {
                _listOfItems.clear()
                _listOfItems.addAll(it)
            }
        }
    }

    var tempList =listOfItems
    val listOfItems get() = _listOfItems

    val sourceItem = mutableStateOf<ListItem?>(null)
    val destionationPosition = mutableStateOf<Int?>(null)



    fun getCurrentDraggedItem(item: ListItem) {
        sourceItem.value = item

    }

    fun getDestionationPositionForDraggedItem(item: ListItem?) {

        destionationPosition.value = listOfItems.indexOf(item)
        Log.e("Positions", "DestionationPosition ViewModel:  ${destionationPosition.value}")

    }

    fun resetDraggedAndDroppedItems() {
        sourceItem.value = null
        destionationPosition.value = null

    }

    fun rearengeTheList() {
        if (sourceItem.value != null && destionationPosition.value != null) {
            tempList.remove(sourceItem.value)
            tempList.add(destionationPosition.value!!, sourceItem.value!!)
            updateList()
        }

    }

    fun updateList(): MutableList<ListItem> {
        _listOfItems = tempList
        return (listOfItems)
    }

    fun itemOnClickExtented(item: ListItem) {
        //tempList[tempList.indexOf(item)] = item.onClick()
        updateList()
    }









}