package com.alexsapps.gridcontentapplication

import android.annotation.SuppressLint

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.alexsapps.gridcontentapplication.ui.theme.GridContentApplicationTheme
import kotlinx.coroutines.flow.MutableStateFlow


class MainActivity : ComponentActivity() {
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GridContentApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {




                    //aici am generat o lista sample pentru testare

                    val listItems = mutableListOf<ListItem>()
                    for(x in 1 until 8) {
                        listItems.add(ListItem(text = "Item $x", color = mutableStateOf(Color.Green)))
                    }
                    val generatedListForGrid: MutableStateFlow<MutableList<ListItem>> = MutableStateFlow(listItems)




                    val viewModel = GridViewModel(generatedListForGrid)

                    GridContent(listToDisplayInGrid = viewModel.listOfItems,
                        getCurrentDraggedItem = {
                                item -> viewModel.getCurrentDraggedItem(item)
                        },
                        getDestinationPositionForDraggedItem = { item ->
                            viewModel.getDestinationPositionForDraggedItem(item)
                        },
                        resetDraggedAndDroppedItems = {
                            viewModel.resetDraggedAndDroppedItems()
                        },

                        reorderTheList = {
                            viewModel.updateList()
                        },
                        )
                }
            }
        }
    }
}










