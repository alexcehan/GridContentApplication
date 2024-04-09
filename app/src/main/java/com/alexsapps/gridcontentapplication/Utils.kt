package com.alexsapps.gridcontentapplication

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun EmptyAddBox() {
    Box(
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .height(155.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(width = 4.dp, color = Color.Gray, shape = RoundedCornerShape(10.dp))
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.addicon),
                contentDescription = "Add Icon",
                modifier = Modifier.fillMaxSize(0.6f)
            )


        }
    }
}

@Composable
fun getColumnsToSpan(item: ListItem): Dp {
    val columnsToSpan by animateDpAsState(targetValue = if(item.stateFullSize.value == 1) Dp(1f) else Dp(2f))
    return columnsToSpan

}