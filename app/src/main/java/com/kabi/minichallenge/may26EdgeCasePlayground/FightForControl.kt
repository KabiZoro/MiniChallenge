@file:OptIn(ExperimentalFoundationApi::class)

package com.kabi.minichallenge.may26EdgeCasePlayground

import androidx.compose.animation.core.spring
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kabi.minichallenge.R
import kotlin.math.roundToInt

enum class CustomSheetValue {
    Collapsed,
    PartiallyExpanded,
    Expanded
}

@Composable
fun FightForControl(
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }

    var selectedBackgroundImageResId by remember { mutableStateOf<Int?>(null) }
    val gridState = rememberLazyGridState()

    val collapsedHeight = with(density) { 70.dp.toPx() }
    val partialHeight = with(density) { 320.dp.toPx() }

    val anchors = remember(screenHeightPx) {
        DraggableAnchors {
            CustomSheetValue.Collapsed at screenHeightPx - collapsedHeight
            CustomSheetValue.PartiallyExpanded at screenHeightPx - partialHeight
            CustomSheetValue.Expanded at 0f
        }
    }

    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val state = remember(anchors) {
        AnchoredDraggableState(
            initialValue = CustomSheetValue.Collapsed,
            anchors = anchors,
            positionalThreshold = { distance -> distance * 0.5f },
            velocityThreshold = { with(density) { 100.dp.toPx() } },
            snapAnimationSpec = spring(),
            decayAnimationSpec = decayAnimationSpec
        )
    }

    val nestedScrollConnection = remember(state, gridState, anchors) {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val partialOffset = anchors.positionOf(CustomSheetValue.PartiallyExpanded)

                // Swiping up
                return if (delta < 0) {
                    // If we are below PartiallyExpanded, move the sheet
                    if (state.offset > partialOffset) {
                        val consumed = state.dispatchRawDelta(delta)
                        Offset(0f, consumed)
                    } else {
                        Offset.Zero
                    }
                }
                // Swiping down (delta > 0)
                else {
                    // If content is at top, move the sheet down
                    if (gridState.firstVisibleItemIndex == 0 && gridState.firstVisibleItemScrollOffset == 0) {
                        val consumed = state.dispatchRawDelta(delta)
                        Offset(0f, consumed)
                    } else {
                        Offset.Zero
                    }
                }
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                val delta = available.y
                // Swiping up
                return if (delta < 0) {
                    // If content finished scrolling (reached bottom), move the sheet to Expanded
                    val consumedBySheet = state.dispatchRawDelta(delta)
                    Offset(0f, consumedBySheet)
                } else {
                    Offset.Zero
                }
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                val velocity = available.y
                val partialOffset = anchors.positionOf(CustomSheetValue.PartiallyExpanded)

                // If sheet is between Collapsed and PartiallyExpanded, handle fling for sheet
                if (velocity < 0 && state.offset > partialOffset) {
                    state.settle(velocity)
                    return available
                }
                // If sheet is at top and we fling down at top of list
                if (velocity > 0 && gridState.firstVisibleItemIndex == 0 && gridState.firstVisibleItemScrollOffset == 0) {
                    state.settle(velocity)
                    return available
                }
                return super.onPreFling(available)
            }

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                state.settle(available.y)
                return available
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
        // Background Scene
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF87CEEB),
                            Color(0xFFFFB6C1),
                            Color(0xFFFF69B4),
                            Color(0xFF40E0D0)
                        )
                    )
                )
        ) {
            selectedBackgroundImageResId?.let { resId ->
                Image(
                    painter = painterResource(id = resId),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        // Custom Bottom Sheet
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset {
                    IntOffset(
                        x = 0,
                        y = state.offset.roundToInt()
                    )
                }
                .anchoredDraggable(state, Orientation.Vertical)
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(Color(0xFFF8F9FF))
        ) {
            SheetContent(
                gridState = gridState,
                onImageClick = { resId -> selectedBackgroundImageResId = resId }
            )
        }
    }
}

@Composable
private fun SheetContent(
    gridState: LazyGridState,
    onImageClick: (Int) -> Unit
) {
    val categories = listOf("Nature", "Travel", "City", "Food", "Animals", "People")
    var selectedCategory by remember { mutableStateOf("Nature") }

    var selectedImageIndex by remember { mutableStateOf<Int?>(null) }
    var selectedImageCategory by remember { mutableStateOf<String?>(null) }

    val allItems = remember {
        val list = mutableListOf<Pair<Int, String>>()
        categories.forEach { category ->
            repeat(8) { i ->
                list.add(Pair(i, category))
            }
        }
        list
    }

    val filteredItems = remember(selectedCategory) {
        allItems.filter { it.second == selectedCategory }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .width(32.dp)
                    .height(4.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF526881))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Swipe up to explore",
                color = Color(0xFF526881),
                fontWeight = FontWeight.SemiBold,
                fontFamily = dmSansFamily
            )
        }

        // Main Scrollable Content
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = gridState,
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            // Categories - Part of the scrollable grid
            item(span = { GridItemSpan(maxLineSpan) }) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(32.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(categories) { category ->
                        val isSelected = category == selectedCategory
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { selectedCategory = category }
                        ) {
                            Text(
                                text = category,
                                color = if (isSelected) Color(0xFF118CFF) else Color(0xFF74777F),
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontSize = 14.sp
                                ),
                                fontFamily = dmSansFamily,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                modifier = Modifier.padding(bottom = 8.dp, top = 8.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .width(if (isSelected) 40.dp else 0.dp)
                                    .height(3.dp)
                                    .background(Color(0xFF118CFF))
                            )
                        }
                    }
                }
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Image Grid
            items(filteredItems) { item ->
                val (index, category) = item
                val isSelected = category == selectedImageCategory && index == selectedImageIndex
                ImageCard(
                    isSelected = isSelected,
                    index = index,
                    category = category,
                    onClick = { resId ->
                        selectedImageIndex = index
                        selectedImageCategory = category
                        onImageClick(resId)
                    }
                )
            }
        }
    }
}

@Composable
fun ImageCard(
    isSelected: Boolean,
    index: Int,
    category: String,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val categorySlug = when (category) {
        "Animals" -> "animal"
        else -> category.lowercase()
    }
    // We have 8 images per category (1 to 8)
    val imageNumber = (index % 8) + 1
    val resourceName = "${categorySlug}${imageNumber}"

    val resId = remember(resourceName) {
        val id = context.resources.getIdentifier(resourceName, "drawable", context.packageName)
        if (id == 0) R.drawable.nature1 else id // Fallback if image not found
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick(resId) }
            .then(
                if (isSelected) {
                    Modifier.border(
                        BorderStroke(2.dp, Color(0xFF118CFF)),
                        RoundedCornerShape(16.dp)
                    )
                } else Modifier
            )
            .padding(if (isSelected) 4.dp else 0.dp)
    ) {
        Image(
            painter = painterResource(id = resId),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(if (isSelected) 12.dp else 16.dp)),
            contentScale = ContentScale.Crop
        )

        if (isSelected) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
                    .size(20.dp)
                    .background(Color.White, CircleShape)
                    .background(Color(0xFF118CFF), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun FightForControlPreview() {
    FightForControl()
}
