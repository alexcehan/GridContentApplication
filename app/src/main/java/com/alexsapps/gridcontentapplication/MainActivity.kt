package com.alexsapps.gridcontentapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexsapps.gridcontentapplication.ui.theme.GridContentApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GridContentApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val listOfStrings = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
                    val listItems = listOfStrings.map { string -> ListItem(text = string) }

                    val generatedListForGrid by remember {
                        mutableStateOf(listItems.toMutableList())

                    }
                    
                    GridContent(listToDisplatInGrid = generatedListForGrid)

                }
            }
        }
    }
}

@Composable
fun GridContent(listToDisplatInGrid: MutableList<ListItem>) {
    LazyVerticalGrid(columns = GridCells.Fixed(2),

        modifier = Modifier.padding(12.dp)
    ) {
        listToDisplatInGrid.forEach { item ->
            var x = item.stateFullSize.value

            item(
                key = item.hashCode(),
                span = {GridItemSpan(x)}
            ) {
                ItemBox(item = item) {
                    item.onClick()
                }
            }
        }

    }
    
}

@Preview(showBackground = true)
@Composable
fun ItemBox(
    modifier: Modifier = Modifier,
    item: ListItem = ListItem(text = "Test Preview", color =  Color.Green),
    onClick: () -> Unit = {}) {



    Box(modifier = Modifier
        .padding(6.dp)
        .clip(shape = RoundedCornerShape(16.dp))
        .height(155.dp)
        .fillMaxWidth()
        .background(item.color)

    ) {
        Row(modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.End) {
            Image(painter = painterResource(id = R.drawable.expandarrows),
                contentDescription = "Arrows",
                modifier = Modifier
                    .padding(6.dp)
                    .size(12.dp)
                    .clickable { onClick.invoke() }


            )
        }

        Row(modifier = Modifier.align(Alignment.Center)) {
            Text(text = item.text,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
            )
        }







    }


}

data class ListItem(

    var text: String,
    var color: Color = Color.Green,
    val stateFullSize: MutableState<Int> = mutableStateOf(1),

) {
    fun onClick() {
        if (stateFullSize.value == 1) {
            stateFullSize.value =2
        } else {
            stateFullSize.value =1
        }
    }


}


