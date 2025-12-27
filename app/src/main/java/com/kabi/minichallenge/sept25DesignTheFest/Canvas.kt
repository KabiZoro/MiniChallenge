@file:OptIn(ExperimentalMaterial3Api::class)

package com.kabi.minichallenge.sept25DesignTheFest

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.rememberScrollable2DState
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.scrollable2D
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun TimelinePainterScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Festival Schedule",
                        color = Color(0xFF786B68),
                        fontSize = 20.sp,
                        fontFamily = parkinsansFamily,
                        fontWeight = FontWeight.Medium,
                        fontStyle = FontStyle.Normal
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFFFFF0)
                ),
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 20.dp
                    )
            )
        },
        containerColor = Color(0xFFFFFFF0)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

            }
        }
    }
}

@Preview
@Composable
private fun TimelinePainterScreenPreview() {
//    TimelinePainterScreen()
}


// Stage Zoom

@Composable
fun StagesZoom(
    modifier: Modifier = Modifier
) {
    val stages = listOf("Main", "Rock", "Electro")
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val state = rememberTransformableState { zoomChange, panChange, _ ->
            scale = (scale * zoomChange).coerceIn(1f, 5f)

            val extraWidth = (scale - 1) * constraints.maxWidth

            val maxX = extraWidth / 2

            offset = Offset(
                x = (offset.x + scale * panChange.x).coerceIn(-maxX, maxX),
                y = 0f
            )
        }

        Row(
            modifier = Modifier
                .graphicsLayer {
                    this.scaleX = scale
                    this.scaleY = scale
                    this.translationX = offset.x
                }
                .padding(16.dp)
                .transformable(state = state),
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            stages.forEach { stage ->
                Text(
                    text = stage,
                    fontSize = 20.sp,
                    fontFamily = parkinsansFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF421E17)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StagesZoomPreview() {
    StagesZoom()
}


// Timeline Zoom

@Composable
fun TimelineZoom(
    modifier: Modifier = Modifier
) {
    val stages = listOf("Main", "Rock", "Electro")
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        val state = rememberTransformableState { zoomChange, panChange, _ ->
            scale = (scale * zoomChange).coerceIn(1f, 2f)

            val extraHeight = (scale - 1) * constraints.maxWidth

            val maxY = extraHeight / 2

            offset = Offset(
                x = 0f,
                y = (offset.y + scale * panChange.y).coerceIn(-maxY, maxY)
            )
        }

        Column(
            modifier = Modifier
                .graphicsLayer {
                    this.scaleX = scale
                    this.scaleY = scale
                    this.translationY = offset.y
                }
                .padding(24.dp)
                .transformable(state = state),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            (9..23).forEach { hour ->
                val timeLabel = String.format("%02d:00", hour)
                Text(
                    text = timeLabel,
                    fontSize = 20.sp,
                    fontFamily = parkinsansFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF421E17)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TimelineZoomPreview() {
    TimelineZoom()
}


// Grid Box

@Composable
fun GridBox(
    modifier: Modifier = Modifier
) {

    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        repeat(3) {
            Column {
                (9..23).forEach { hour ->
                    Row(
                        modifier = Modifier
                    ) {
                        Grid(10, 10)
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun GridBoxPreview() {
    GridBox()
}


// Grid Composable

@Composable
fun Grid(
    h: Int,
    m: Int
) {
    val offset = remember { mutableStateOf(Offset.Zero) }
    Box(
        Modifier
            .size(150.dp)
            .scrollable2D(
                state =
                    rememberScrollable2DState { delta ->
                        offset.value = offset.value + delta
                        delta
                    }
            )
            .background(Color.LightGray),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "$h:$m",
            fontSize = 20.sp,
            fontFamily = parkinsansFamily,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF421E17)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GridPreview() {
    Grid(10, 10)
}



