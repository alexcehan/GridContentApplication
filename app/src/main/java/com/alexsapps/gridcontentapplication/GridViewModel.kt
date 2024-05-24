package com.alexsapps.gridcontentapplication

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class GridViewModel(private val listOfItemsVal: MutableStateFlow<MutableList<ListItem>>): ViewModel() {

    private var _listOfItems: SnapshotStateList<ListItem> = mutableStateListOf()


    init {
        viewModelScope.launch {
            listOfItemsVal.collect {
                _listOfItems.clear()
                _listOfItems.addAll(it)
            }
        }
    }


    val listOfItems get() = _listOfItems

    private val sourceItem = mutableStateOf<ListItem?>(null)
    private val destinationPosition = mutableStateOf<Int?>(null)



    fun getCurrentDraggedItem(item: ListItem) {
        sourceItem.value = item

    }

    fun getDestinationPositionForDraggedItem(item: ListItem?) {

        destinationPosition.value = listOfItems.indexOf(item)

    }

    fun resetDraggedAndDroppedItems() {
        sourceItem.value = null
        destinationPosition.value = null

    }

    fun updateList() {
        if (sourceItem.value != null && destinationPosition.value != null) {
            _listOfItems.remove(sourceItem.value)
            _listOfItems.add(destinationPosition.value!!, sourceItem.value!!)

        }

    }












}