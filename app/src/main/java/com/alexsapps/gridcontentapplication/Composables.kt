package com.alexsapps.gridcontentapplication

import android.content.ClipData
import android.content.ClipDescription
import android.content.Intent
import android.content.res.Resources
import android.util.DisplayMetrics
import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.google.gson.Gson

private const val listItemTransferAction = "action_item"
private const val listItemTransferData = "data_item"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemBox(
    modifier: Modifier,
    itemHeight: MutableState<Dp> = mutableStateOf(160.dp),
    item: ListItem = ListItem(text = "Test Preview", color = mutableStateOf(Color.Green)),
    itemWidth: MutableState<Dp> = mutableStateOf(190.dp),
    getCurrentDraggedItem: (item: ListItem) -> Unit,
    getDestinationPositionForDraggedItem: (item: ListItem?) -> Unit,
    resetDraggedAndDroppedItems: () -> Unit,
    reorderTheList: () -> Unit

) {

    val heightOfItemAnimated by animateDpAsState(
        targetValue = itemHeight.value
    )

    val widthOfItemAnimated by animateDpAsState(
        targetValue = itemWidth.value
    )

    val bgColor by remember {
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
                    reorderTheList.invoke()
                    resetDraggedAndDroppedItems.invoke()
                    return true
                }

                override fun onEntered(event: DragAndDropEvent) {
                    super.onEntered(event)
                    getDestinationPositionForDraggedItem(item)


                    bgColor.value = Color.Red
                }

                override fun onExited(event: DragAndDropEvent) {
                    super.onExited(event)
                    getDestinationPositionForDraggedItem(null)
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
                .height(heightOfItemAnimated)
                .width(widthOfItemAnimated)
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


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GridContent(
    listToDisplayInGrid: SnapshotStateList<ListItem>,
    getCurrentDraggedItem: (item: ListItem) -> Unit,
    getDestinationPositionForDraggedItem: (item: ListItem?) -> Unit,
    resetDraggedAndDroppedItems: () -> Unit,
    reorderTheList: () -> Unit
) {



    LazyVerticalGrid(
        columns = GridCells.Fixed(2),

        modifier = Modifier
            .padding(12.dp)
            .fillMaxHeight()

    ) {





        val displayMetrics: DisplayMetrics = Resources.getSystem().displayMetrics
        val widthOfTheScreen = displayMetrics.widthPixels / displayMetrics.density


        listToDisplayInGrid.forEach { item ->


            val columnsToSpan: MutableState<Int> =  if(item.stateFullSize.value != 1){ mutableIntStateOf(2) }else mutableIntStateOf(1)
            val widthOfItem: MutableState<Dp> = if (columnsToSpan.value == 1) mutableStateOf((widthOfTheScreen / 2).dp) else mutableStateOf((widthOfTheScreen).dp)
            val heightOfItem: MutableState<Dp> =  if(item.stateFullSize.value == 3) mutableStateOf(320.dp) else mutableStateOf(160.dp)


            item(
                key = item.hashCode(),
                span = { GridItemSpan(columnsToSpan.value) }
            ) {

                ItemBox(
                    modifier = Modifier
                    ,
                    item = item,
                    itemWidth = widthOfItem,
                    itemHeight = heightOfItem,
                    getCurrentDraggedItem = { item ->
                        getCurrentDraggedItem(item)
                    },
                    getDestinationPositionForDraggedItem = { item ->
                        getDestinationPositionForDraggedItem(
                            item
                        )

                    },
                    resetDraggedAndDroppedItems = {
                        resetDraggedAndDroppedItems.invoke()
                    },
                    reorderTheList = {
                        reorderTheList.invoke()

                    }
                )
            }
        }
    }
}




