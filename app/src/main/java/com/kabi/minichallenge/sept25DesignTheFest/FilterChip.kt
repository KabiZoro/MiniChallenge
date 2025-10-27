@file:OptIn(ExperimentalMaterial3Api::class)

package com.kabi.minichallenge.sept25DesignTheFest

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kabi.minichallenge.R

@Composable
fun MapScreen(modifier: Modifier = Modifier) {

    val filters = listOf("Stages", "Food", "WC")
    var selectedFilters by remember { mutableStateOf(setOf<String>()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Festival Map",
                        color = Color(0xFF4E342E),
                        fontSize = 30.sp,
                        fontFamily = parkinsansFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = FontStyle.Normal
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFFFFF0)
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    color = Color(0xFFFFFFF0)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // Filter Chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    filters.forEach { label ->
                        val containerColor = when (label) {
                            "Stages" -> Color(0xFFE0E270)
                            "Food" -> Color(0xFFF59BB0)
                            "WC" -> Color(0xFFEC9C50)
                            else -> Color(0xFF786B68)
                        }

                        val icon = when (label) {
                            "Stages" -> R.drawable.stage
                            "Food" -> R.drawable.food
                            "WC" -> R.drawable.wc
                            else -> R.drawable.stage
                        }
                        FilterChipButtons(
                            text = label,
                            onClick = {
                                selectedFilters = if (selectedFilters.contains(label)) {
                                    selectedFilters - label
                                } else {
                                    selectedFilters + label
                                }
                            },
                            selected = selectedFilters.contains(label),
                            contentColor = Color(0xFF421E17),
                            containerColor = containerColor,
                            icon = icon
                        )
                    }
                }

                // Map
                Image(
                    painter = painterResource(id = R.drawable.map),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            // Map Markers
            Box(modifier = Modifier.fillMaxSize()) {
                if (selectedFilters.contains("Stages")) {
                    MapMarkers("Stage A", Color(0xFFFDE047), 0.55f, 0.1f, R.drawable.stage)
                    MapMarkers("Stage B", Color(0xFFFDE047), 0.25f, 0.4f, R.drawable.stage)
                    MapMarkers("Stage C", Color(0xFFFDE047), 0.4f, 0.75f, R.drawable.stage)
                }
                if (selectedFilters.contains("Food")) {
                    MapMarkers("Food A", Color(0xFFFDA4AF), 0.2f, 0.85f, R.drawable.food)
                    MapMarkers("Food B", Color(0xFFFDA4AF), 0.25f, 0.55f, R.drawable.food)
                    MapMarkers("Food C", Color(0xFFFDA4AF), 0.7f, 0.25f, R.drawable.food)
                }
                if (selectedFilters.contains("WC")) {
                    MapMarkers("WC A", Color(0xFFFB923C), 0.15f, 0.2f, R.drawable.wc)
                    MapMarkers("WC B", Color(0xFFFB923C), 0.8f, 0.5f, R.drawable.wc)
                }
            }
        }

    }

}

@Preview
@Composable
private fun MapScreenPreview() {
    MapScreen()
}


// Filter Chip Buttons

@Composable
fun FilterChipButtons(
    text: String,
    onClick: () -> Unit,
    selected: Boolean,
    contentColor: Color,
    containerColor: Color,
    @DrawableRes icon: Int
) {
    FilterChip(
        onClick = onClick,
        label = {
            Text(
                text = text,
                fontSize = 20.sp,
                fontFamily = parkinsansFamily,
                fontWeight = FontWeight.Medium,
                fontStyle = FontStyle.Normal,
                color = if (selected) contentColor
                else Color.Gray
            )
        },
        selected = selected,
        leadingIcon = {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp),
                tint = if (selected) contentColor
                else Color.Gray
            )
        },
        colors = if (selected) {
            FilterChipDefaults.filterChipColors(
                selectedContainerColor = containerColor,
                selectedLabelColor = Color.Black
            )
        } else {
            FilterChipDefaults.filterChipColors(
                selectedContainerColor = Color.Gray,
                labelColor = Color(0xFF786B68)
            )
        },
        shape = CircleShape,
        border = BorderStroke(
            width = 1.dp,
            color = if (selected) containerColor
            else Color(0xFF786B68)
        )
    )
}

@Preview
@Composable
private fun FilterCHipButtonPreview() {
    FilterChipButtons(
        text = "WC",
        onClick = { },
        selected = true,
        contentColor = Color(0xFF421E17),
        containerColor = Color(0xFFEC9C50),
        icon = R.drawable.wc
    )
}


// Map Markers

@Composable
fun MapMarkers(
    label: String,
    containerColor: Color,
    xFraction: Float,
    yFraction: Float,
    @DrawableRes icon: Int
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = (yFraction * 700).dp,
                    start = (xFraction * 400).dp
                )
        ) {
            MapPointer(
                label = label,
                containerColor = containerColor,
                icon = icon
            )
        }
    }
}


// Map Pointer

@Composable
fun MapPointer(
    label: String,
    containerColor: Color,
    @DrawableRes icon: Int
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(containerColor)
            .border(
                width = 1.dp,
                color = Color(0xFF421E17),
                shape = CircleShape
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = Color(0xFF421E17),
                modifier = Modifier
                    .size(16.dp)
            )
            if (label.startsWith("Stage")) {
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = label,
                    color = Color(0xFF421E17),
                    fontSize = 20.sp,
                    fontFamily = parkinsansFamily,
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Normal,
                )
            }
        }
    }
}

@Preview
@Composable
private fun MapPointerPreview() {
    MapPointer(
        label = "Stage A",
        containerColor = Color(0xFFE0E270),
        icon = R.drawable.stage
    )
}
