@file:OptIn(ExperimentalMaterial3Api::class)

package com.kabi.minichallenge.nov25BlackFriday

import android.content.ClipData
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kabi.minichallenge.R
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun HiddenDiscountSwipe(
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }

    var contextMenuWidth by remember {
        mutableFloatStateOf(0f)
    }
    val offset = remember {
        Animatable(initialValue = 0f)
    }

    val scope = rememberCoroutineScope()
    var isRevealed by remember {
        mutableStateOf(false)
    }
    val clipboardManager = LocalClipboardManager.current

    var promoCode by remember {
        mutableStateOf("")
    }
    var isDiscountApplied by remember {
        mutableStateOf(false)
    }
    var isCodeCorrect by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = isRevealed, contextMenuWidth) {
        if (isRevealed) {
            offset.animateTo(-contextMenuWidth)
        } else {
            offset.animateTo(0f)
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    containerColor = Color(0xFF211304).copy(0.7f),
                    contentColor = Color(0xFFFFFFFF),
                    content = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = data.visuals.message,
                                style = TextStyle(
                                    textAlign = TextAlign.Center,
                                    fontFamily = hostGroteskFamily,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                )
            }
        },
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.arrow_left),
                            contentDescription = null,
                            tint = Color(0xFF211304),
                            modifier = Modifier
                                .size(20.dp)
                        )
                    }
                },
                title = {
                    Text(
                        text = "Cart",
                        fontFamily = hostGroteskFamily,
                        fontSize = 22.sp,
                        lineHeight = 28.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF211304)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF6F2ED),
                    titleContentColor = Color(0xFF211304),
                    navigationIconContentColor = Color(0xFF211304)
                )
            )
        },
        containerColor = Color(0xFFF6F2ED)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)
        ) {
            PromoCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerInput(isRevealed) {
                        detectHorizontalDragGestures { _, dragAmount ->
                            if (dragAmount > 10 && isRevealed) {
                                isRevealed = false
                            }
                        }
                    },
                onCopyClick = {
                    val clipData = ClipData.newPlainText("BF2025", "BF2025")
                    val clipEntry = ClipEntry(clipData)
                    clipboardManager.setClip(clipEntry)
                    isRevealed = false

                    scope.launch {
                        snackbarHostState.showSnackbar("Copied!")
                    }
                }
            )
            CardItem(
                modifier = Modifier
                    .onSizeChanged {
                        contextMenuWidth = it.width.toFloat()
                    }
                    .offset { IntOffset(offset.value.roundToInt(), 0) }
                    .pointerInput(contextMenuWidth) {
                        detectHorizontalDragGestures(
                            onHorizontalDrag = { _, dragAmount ->
                                scope.launch {
                                    val newOffset = (offset.value + dragAmount)
                                        .coerceIn(-contextMenuWidth, 0f)
                                    offset.snapTo(newOffset)
                                }
                            },
                            onDragEnd = {
                                when {
                                    offset.value <= -contextMenuWidth / 5f -> {
                                        scope.launch {
                                            isRevealed = true
                                            offset.animateTo(-contextMenuWidth)
                                        }
                                    }

                                    else -> {
                                        scope.launch {
                                            isRevealed = false
                                            offset.animateTo(0f)
                                        }
                                    }
                                }
                            }
                        )
                    },
                isCodeCorrect = isCodeCorrect
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                ) {
                    Row(
                        modifier = Modifier
                            .height(IntrinsicSize.Max),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = promoCode,
                            onValueChange = { promoCode = it },
                            shape = RoundedCornerShape(1.dp),
                            placeholder = {
                                Text(
                                    text = "Enter Promo Code",
                                    fontFamily = hostGroteskFamily,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0XFF9A9795)
                                )
                            },
                            enabled = if (isDiscountApplied && isCodeCorrect) false else true,
                            singleLine = true,
                            maxLines = 1,
                            textStyle = TextStyle(
                                fontFamily = hostGroteskFamily,
                                fontSize = 16.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0XFF211304)
                            ),
                            isError = isDiscountApplied && !isCodeCorrect,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFFDFDDDB),
                                unfocusedBorderColor = Color(0xFFDFDDDB),
                                errorBorderColor = Color(0xFF7C1414)
                            ),
                            modifier = Modifier
                        )
                        Button(
                            onClick = {
                                val promoCodeString = clipboardManager.getText()
                                isCodeCorrect = promoCode == promoCodeString?.text
                                isDiscountApplied = true

                                if (isCodeCorrect) {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Discount applied")
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f),
                            shape = RoundedCornerShape(1.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFDFDDDB),
                                contentColor = Color(0xFF211304)
                            )
                        ) {
                            Text(
                                text = if (isDiscountApplied && isCodeCorrect) "Applied" else "Apply",
                                fontFamily = hostGroteskFamily,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0XFF211304),
                                modifier = Modifier
                            )
                        }
                    }
                    if (isDiscountApplied && !isCodeCorrect){
                        Text(
                            text = "Invalid code",
                            fontFamily = hostGroteskFamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0XFF7C1414),
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(1.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF211304),
                            contentColor = Color(0xFFFFFFFF)
                        )
                    ) {
                        Text(
                            text = "Buy",
                            fontFamily = hostGroteskFamily,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0XFFFFFFFF)
                        )
                    }
                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
        }
    }
}

@Composable
fun PromoCard(
    modifier: Modifier = Modifier,
    onCopyClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFD33F3F),
                        Color(0xFF7C1414)
                    )
                )
            )
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "BLACK FRIDAY SALE",
                    fontFamily = hostGroteskFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0XFFFFFFFF).copy(0.7f)
                )
                Spacer(modifier = Modifier.height(21.dp))
                Box(
                    modifier = Modifier,
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "25%",
                        fontFamily = hostGroteskFamily,
                        fontSize = 44.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0XFFFFFFFF),
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .offset(y = (-8).dp)
                    )
                    Text(
                        text = "OFF",
                        fontFamily = hostGroteskFamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0XFFFFFFFF),
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                    )
                }
            }
            Box {
                Icon(
                    imageVector = Icons.Default.Circle,
                    contentDescription = null,
                    tint = Color(0XFFF6F2ED),
                    modifier = Modifier
                        .size(20.dp)
                        .offset(y = 88.dp)
                )
                Box(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .height(80.dp)
                        .width(1.dp)
                        .drawWithContent {
                            drawLine(
                                color = Color(0xFFFFFFFF).copy(alpha = 0.2f),
                                start = Offset(0f, 0f),
                                end = Offset(0f, size.height),
                                pathEffect = PathEffect.dashPathEffect(
                                    floatArrayOf(8.dp.toPx(), 6.dp.toPx())
                                ),
                                strokeWidth = 2f
                            )
                        }
                )
                Icon(
                    imageVector = Icons.Default.Circle,
                    contentDescription = null,
                    tint = Color(0XFFF6F2ED),
                    modifier = Modifier
                        .size(20.dp)
                        .offset(y = (-28).dp)
                )
            }
            Column(
                modifier = Modifier
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier,
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                    ) {
                        Box(
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFFFFFFFF).copy(0.2f)
                                )
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 6.dp
                                )
                        ) {
                            Text(
                                text = "BF2025",
                                fontFamily = hostGroteskFamily,
                                fontSize = 24.sp,
                                color = Color(0xFFFFFFFF),
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color(0xFFFFFFFF))
                                .clickable { onCopyClick() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.copy),
                                contentDescription = null,
                                tint = Color(0xFF7C1414),
                                modifier = Modifier
                                    .size(15.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PromoCardPreview() {
    PromoCard(
        onCopyClick = {}
    )
}

@Composable
fun CardItem(
    modifier: Modifier = Modifier,
    isCodeCorrect: Boolean = false
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color(0xFFFFFFFF))
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            Image(
                painter = painterResource(R.drawable.airpods),
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(96.dp)
            )
            Column(
                modifier = Modifier
                    .padding(end = 8.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Google Pixel Buds Pro",
                    fontFamily = hostGroteskFamily,
                    fontSize = 20.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF211304)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Noise-cancelling wireless earbuds with rich sound and long battery life.",
                    fontFamily = hostGroteskFamily,
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF9A9795)
                )
                Spacer(modifier = Modifier.height(18.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(1.dp))
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFFDFDDDB),
                                )
                                .size(22.dp)
                                .clickable {},
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.minus),
                                contentDescription = null,
                                tint = Color(0xFF9A9795),
                                modifier = Modifier
                                    .size(14.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(18.dp))
                        Text(
                            text = "1",
                            fontFamily = hostGroteskFamily,
                            fontSize = 16.sp,
                            lineHeight = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF211304),
                            modifier = Modifier
                        )
                        Spacer(modifier = Modifier.width(18.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(1.dp))
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFFDFDDDB),
                                )
                                .size(22.dp)
                                .clickable {},
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.plus),
                                contentDescription = null,
                                tint = Color(0xFF211304),
                                modifier = Modifier
                                    .size(14.dp)
                            )
                        }
                    }
                    if (isCodeCorrect) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "$149",
                                fontFamily = hostGroteskFamily,
                                fontSize = 24.sp,
                                lineHeight = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF211304),
                                modifier = Modifier
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "$199",
                                fontFamily = hostGroteskFamily,
                                fontSize = 16.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF9A9795),
                                textDecoration = TextDecoration.LineThrough,
                                modifier = Modifier
                            )
                        }
                    } else {
                        Text(
                            text = "$199",
                            fontFamily = hostGroteskFamily,
                            fontSize = 24.sp,
                            lineHeight = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF211304),
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CardItemPreview() {
    CardItem()
}

@Preview
@Composable
private fun HiddenDiscountSwipePreview() {
    HiddenDiscountSwipe()
}