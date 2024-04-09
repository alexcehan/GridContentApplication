package com.alexsapps.gridcontentapplication

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipDescription
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draganddrop.mimeTypes
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexsapps.gridcontentapplication.ui.theme.GridContentApplicationTheme
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.random.Random


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

//TODO: _____________________________________________________________________



                    val listItems = mutableListOf<ListItem>()


                    for(x in 1 until 11) {
                        listItems.add(ListItem(text = "Item $x", color = mutableStateOf(Color.Green)))
                    }


                    listItems.forEach { item ->
                        run {if(item.text != "") {
                            item.isBoxFilled.value = true


                        }


                        }

                    }



//                    var generatedListForGrid2 = remember {
//                        mutableStateListOf<ListItem>()
//
//                    }

//                    listItems.forEach {
//                        generatedListForGrid2.add(it)
//                    }

                    var generatedListForGrid: MutableStateFlow<MutableList<ListItem>> = MutableStateFlow(listItems)
//TODO ___________________________________________________________________________


                    val viewModel = GridViewModel(generatedListForGrid)








                    GridContent(listToDisplatInGrid = viewModel.listOfItems,
                        getCurrentDraggedItem = {

                                item -> viewModel.getCurrentDraggedItem(item)

                            Log.e(
                                "Positions",
                                "GetCurrentDraggedItem: Executed at top level ${viewModel.sourceItem} , $item",
                            )
                        },
                        getDestionationPositionForDraggedItem = { item ->
                            viewModel.getDestionationPositionForDraggedItem(item)

                        },
                        resetDraggedAndDroppedItems = {
                            viewModel.resetDraggedAndDroppedItems()

                        },
                        itemOnClick = {item ->
                                      viewModel.itemOnClickExtented(item)
                        },
                        rearengeTheList = {

                            viewModel.rearengeTheList()
                           // generatedListForGrid.value = viewModel.updateList()


//                            if (sourceItem != null && destinationPosition != null) {
//                                Log.e("Positions", "IF STATEMENT EXECUTED!!!!!!!!!!!")
//
//                                val sourcePosition = generatedListForGrid.value.indexOf(sourceItem)
//                                val tempItem = ListItem()
//                                generatedListForGrid.value.remove(sourceItem)
//                                //generatedListForGrid.add(sourcePosition, tempItem)
//                                generatedListForGrid.value.add(destinationPosition!!, sourceItem!!)
//                                //generatedListForGrid.remove(tempItem)
//
//
//                                Log.e("Positions", "$generatedListForGrid")
//
//
//                            }

                        },
                        )
                }
            }
        }
    }
}



@Composable
fun GridContent(
    listToDisplatInGrid: SnapshotStateList<ListItem>,
    getCurrentDraggedItem: (item: ListItem) -> Unit,
    getDestionationPositionForDraggedItem: (item: ListItem?) -> Unit,
    resetDraggedAndDroppedItems: () -> Unit,
    itemOnClick: (listItem: ListItem) -> Unit,
    rearengeTheList: () -> Unit
) {



    LazyVerticalGrid(
        columns = GridCells.Fixed(2),

        modifier = Modifier
            .padding(12.dp)
            .fillMaxHeight()
    ) {








        listToDisplatInGrid.forEach { item ->


           var columnsToSpan =  if(item.stateFullSize.value != 1) 2 else 1 //if (item.stateFullSize.value == 1) 1 else 2
          //  var heightOfItem = 160.dp//if (item.stateFullSize.value == 3) 320.dp else 160.dp


            var heightOfItem =  if(item.stateFullSize.value == 3) 320.dp else 160.dp



                item(
                    key = item.hashCode(),
                    span = { GridItemSpan(columnsToSpan) }
                ) {

                    ItemBox(modifier = Modifier,item = item,
                        itemHeight = heightOfItem,
                        getCurrentDraggedItem = { item ->
                            getCurrentDraggedItem(item)
                            Log.e("DraggedItem", "Dragging: ${item.text} ", )},
                        getDestionationPositionForDraggedItem = { item ->
                            getDestionationPositionForDraggedItem(
                                item
                            )
                            Log.e("DraggedItem", "Dragging Over: ${item?.text} ",)
                            Log.d("DraggedItem", "List: ${listToDisplatInGrid} ")
                        },
                        resetDraggedAndDroppedItems = {
                            resetDraggedAndDroppedItems.invoke()
                        },
                        rearengeTheList = {
                            rearengeTheList.invoke()

                        }
                    )
                }
            }


        }


    }



private const val listItemTransferAction = "action_item"
private const val listItemTransferData = "data_item"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemBox(
    modifier: Modifier = Modifier
        ,
    itemHeight: Dp = 160.dp,
    item: ListItem = ListItem(text = "Test Preview", color = mutableStateOf(Color.Green)),
    getCurrentDraggedItem: (item: ListItem) -> Unit,
    getDestionationPositionForDraggedItem: (item: ListItem?) -> Unit,
    resetDraggedAndDroppedItems: () -> Unit,
    rearengeTheList: () -> Unit

) {

    var bgColor by remember {
        mutableStateOf(item.color)
    }



    Box(modifier = Modifier

        .dragAndDropTarget(
            shouldStartDragAndDrop = { event ->
                event
                    .mimeTypes()
                    .contains(ClipDescription.MIMETYPE_TEXT_INTENT)
            },
            target = object : DragAndDropTarget {
                override fun onDrop(event: DragAndDropEvent): Boolean {
                    bgColor.value = Color.Green
                    rearengeTheList.invoke()
                    resetDraggedAndDroppedItems.invoke()
                    return true
                }

                override fun onEntered(event: DragAndDropEvent) {
                    super.onEntered(event)
                    getDestionationPositionForDraggedItem(item)
                    Log.e("Positions", "onEntered:  $item")
                    Log.e("Positions", "DestionationPosition Composable:  ${item.text}")

                    bgColor.value = Color.Red
                }

                override fun onExited(event: DragAndDropEvent) {
                    super.onExited(event)
                    getDestionationPositionForDraggedItem(null)
                    bgColor.value = Color.Green
                }
            }
        )
        .dragAndDropSource {

            detectTapGestures(onLongPress = {
                getCurrentDraggedItem(item)

                startTransfer(DragAndDropTransferData(
                    clipData = ClipData.newIntent(
                        "item",
                        Intent(listItemTransferAction).apply {
                            putExtra(listItemTransferData, Gson().toJson(item))
                        }
                    )
                ))


            }) { }
        }
    ) {
        Box(
            modifier = Modifier
                .padding(6.dp)
                .clip(shape = RoundedCornerShape(16.dp))
                .height(itemHeight)
                .fillMaxWidth()
                .background(bgColor.value)


        ) {
            Row(
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Image(painter = painterResource(id = R.drawable.expandarrows),
                    contentDescription = "Arrows",
                    modifier = Modifier
                        .padding(6.dp)
                        .size(12.dp)
                        .clickable { item.onClick() }


                )
            }

            Row(modifier = Modifier.align(Alignment.Center)) {
                Text(
                    text = item.text,
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                )
            }
        }

    }


}





