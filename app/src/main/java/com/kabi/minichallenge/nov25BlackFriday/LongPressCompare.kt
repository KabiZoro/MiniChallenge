@file:OptIn(ExperimentalMaterial3Api::class)

package com.kabi.minichallenge.nov25BlackFriday

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.kabi.minichallenge.R
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {
    @Serializable
    data object ComparisonScreen : Route

    @Serializable
    data object MainScreen : Route
}

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier
) {
    val backStack = rememberNavBackStack(Route.MainScreen)
    val resultStore = rememberResultStore()

    var selectedPhoneIds by remember {
        mutableStateOf(emptyList<Int>())
    }

    NavDisplay(
        modifier = Modifier,
        backStack = backStack,
        transitionSpec = {
            slideInHorizontally { it } + fadeIn() togetherWith
                    slideOutHorizontally { -it } + fadeOut()
        },
        popTransitionSpec = {
            slideInHorizontally { -it } + fadeIn() togetherWith
                    slideOutHorizontally { it } + fadeOut()
        },
        predictivePopTransitionSpec = {
            slideInHorizontally { -it } + fadeIn() togetherWith
                    slideOutHorizontally { it } + fadeOut()
        },
        entryProvider = entryProvider {
            entry<Route.MainScreen> {
                LongPressCompare(
                    resultStore = resultStore,
                    selectedPhoneIds = selectedPhoneIds,
                    onSelectedChange = { selectedPhoneIds = it },
                    onNavigateToComparisonScreen = {
                        backStack.add(Route.ComparisonScreen)
                    }
                )
            }
            entry<Route.ComparisonScreen> {
                ComparisonScreen(
                    resultStore = resultStore,
                    onNavigateToListScreen = {
                        backStack.remove(Route.ComparisonScreen)
                    }
                )
            }
        }
    )
}

@Stable
class ResultStore {
    private val results = mutableMapOf<Any, Any?>()

    @Suppress("UNCHECKED_CAST")
    fun <T> getResult(key: Any): T? = results[key] as? T

    fun <T> setResult(key: Any, value: T) {
        results[key] = value
    }

    fun removeResult(key: Any) {
        results.remove(key)
    }

    companion object {
        val Saver = Saver<ResultStore, Map<Any, Any?>>(
            save = { it.results.toMap() },
            restore = {
                ResultStore().apply { results.putAll(it) }
            }
        )
    }
}

@Composable
fun rememberResultStore() = rememberSaveable(
    saver = ResultStore.Saver
) {
    ResultStore()
}

@Composable
fun ComparisonScreen(
    resultStore: ResultStore,
    onNavigateToListScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val result = resultStore.getResult<List<Phone>>("selectedPhones")
    val phone1 = result?.get(0)
    val phone2 = result?.get(1)
    Scaffold(
        modifier = Modifier,
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateToListScreen
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.arrow_left),
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp)
                        )
                    }
                },
                title = {
                    Text(
                        text = "Comparison",
                        fontFamily = hostGroteskFamily,
                        fontSize = 22.sp,
                        color = Color(0xFF041221),
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFEDF0F6),
                    titleContentColor = Color(0xFF041221),
                    navigationIconContentColor = Color(0xFF041221),
                )
            )
        },
        containerColor = Color(0xFFEDF0F6)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
                .padding(paddingValues)
                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                .background(Color(0xFFFFFFFF)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                phone1?.let {
                    phone2?.let {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            PhoneItemLayout(
                                phone = Phone(
                                    id = phone1.id,
                                    image = phone1.image,
                                    title = phone1.title,
                                    newPrice = phone1.newPrice,
                                    oldPrice = phone1.oldPrice,
                                    discount = phone1.discount,
                                    display = phone1.display,
                                    mainCamera = phone1.mainCamera,
                                    frontCamera = phone1.frontCamera,
                                    processor = phone1.processor,
                                    ram = phone1.ram,
                                    storage = phone1.storage,
                                    battery = phone1.battery
                                ),
                                size = 184.dp,
                                modifier = Modifier
                                    .weight(1f)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            PhoneItemLayout(
                                phone = Phone(
                                    id = phone2.id,
                                    image = phone2.image,
                                    title = phone2.title,
                                    newPrice = phone2.newPrice,
                                    oldPrice = phone2.oldPrice,
                                    discount = phone2.discount,
                                    display = phone2.display,
                                    mainCamera = phone2.mainCamera,
                                    frontCamera = phone2.frontCamera,
                                    processor = phone2.processor,
                                    ram = phone2.ram,
                                    storage = phone2.storage,
                                    battery = phone2.battery
                                ),
                                size = 184.dp,
                                modifier = Modifier
                                    .weight(1f)
                            )
                        }
                    }
                }
                HorizontalDiv()
                TitleText("Display")
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DetailText(
                        text = "${phone1?.title}\"",
                        modifier = Modifier
                            .weight(1f)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    DetailText(
                        text = "${phone2?.title}\"",
                        modifier = Modifier
                            .weight(1f)
                    )
                }
                HorizontalDiv()
                TitleText("Main Camera")
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DetailText(
                        text = "${phone1?.mainCamera}MP",
                        modifier = Modifier
                            .weight(1f)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    DetailText(
                        text = "${phone2?.mainCamera}MP",
                        modifier = Modifier
                            .weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                TitleText("Front Camera")
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DetailText(
                        text = "${phone1?.frontCamera}MP",
                        modifier = Modifier
                            .weight(1f)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    DetailText(
                        text = "${phone2?.frontCamera}MP",
                        modifier = Modifier
                            .weight(1f)
                    )
                }
                HorizontalDiv()
                TitleText("Processor")
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DetailText(
                        text = "${phone1?.processor}",
                        modifier = Modifier
                            .weight(1f)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    DetailText(
                        text = "${phone2?.processor}",
                        modifier = Modifier
                            .weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                TitleText("RAM")
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DetailText(
                        text = "${phone1?.ram}GB",
                        modifier = Modifier
                            .weight(1f)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    DetailText(
                        text = "${phone2?.ram}GB",
                        modifier = Modifier
                            .weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                TitleText("Storage")
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DetailText(
                        text = "${phone1?.storage}GB",
                        modifier = Modifier
                            .weight(1f)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    DetailText(
                        text = "${phone2?.storage}GB",
                        modifier = Modifier
                            .weight(1f)
                    )
                }
                HorizontalDiv()
                TitleText("Battery(mAh)")
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DetailText(
                        text = "${phone1?.battery}mAh",
                        modifier = Modifier
                            .weight(1f)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    DetailText(
                        text = "${phone1?.battery}mAh",
                        modifier = Modifier
                            .weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun TitleText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontFamily = hostGroteskFamily,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        color = Color(0xFF526881),
        fontWeight = FontWeight.Normal,
        modifier = modifier
    )
}

@Composable
fun DetailText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontFamily = hostGroteskFamily,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        color = Color(0xFF041221),
        fontWeight = FontWeight.Normal,
        modifier = modifier
            .fillMaxWidth()
    )
}

@Composable
fun HorizontalDiv(modifier: Modifier = Modifier) {
    HorizontalDivider(
        thickness = 1.dp,
        color = Color(0xFFD8E4EA),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    )
}

@Preview
@Composable
private fun ComparisonScreenPreview() {
    ComparisonScreen(
        resultStore = rememberResultStore(),
        onNavigateToListScreen = {}
    )
}

@Composable
fun LongPressCompare(
    resultStore: ResultStore,
    onNavigateToComparisonScreen: () -> Unit,
    selectedPhoneIds: List<Int>,
    onSelectedChange: (List<Int>) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = Modifier,
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (selectedPhoneIds.isNotEmpty()) {
                                onSelectedChange(emptyList())
                            }
                        }
                    ) {
                        Icon(
                            painter = if (selectedPhoneIds.isNotEmpty())
                                painterResource(R.drawable.arrow_left)
                            else painterResource(R.drawable.menu),
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp)
                        )
                    }
                },
                title = {
                    Text(
                        text = if (selectedPhoneIds.isNotEmpty())
                            "${selectedPhoneIds.size} item selected" else "TechStore",
                        fontFamily = hostGroteskFamily,
                        fontSize = 22.sp,
                        color = if (selectedPhoneIds.isNotEmpty())
                            Color(0xFFA8B7BF) else Color(0xFF041221),
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (selectedPhoneIds.size >= 2) {
                                val selectedPhones = phoneItemList.filter {
                                    selectedPhoneIds.contains(it.id)
                                }
                                resultStore.setResult("selectedPhones", selectedPhones)
                                onNavigateToComparisonScreen()
                            }
                        }
                    ) {
                        if (selectedPhoneIds.isNotEmpty()) {
                            if (selectedPhoneIds.size == 1) {
                                Icon(
                                    painter = painterResource(R.drawable.scales),
                                    contentDescription = null,
                                    tint = Color(0xFFA8B7BF),
                                    modifier = Modifier
                                        .size(20.dp)
                                )
                            } else {
                                Icon(
                                    painter = painterResource(R.drawable.scales),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(20.dp)
                                )
                            }
                        } else {
                            Icon(
                                painter = painterResource(R.drawable.shopping_cart),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFEDF0F6),
                    titleContentColor = Color(0xFF041221),
                    navigationIconContentColor = Color(0xFF041221),
                    actionIconContentColor = Color(0xFF041221)
                )
            )
        },
        containerColor = Color(0xFFEDF0F6)
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(
                items = phoneItemList,
                key = { phone -> phone.id }
            ) { phone ->
                PhoneItem(
                    phone = phone,
                    isSelected = selectedPhoneIds.contains(phone.id),
                    modifier = Modifier
                        .pointerInput(selectedPhoneIds) {
                            detectTapGestures(
                                onTap = {
                                    val newSelection = selectedPhoneIds.toMutableList()
                                    if (newSelection.contains(phone.id)) {
                                        newSelection.remove(phone.id)
                                    } else {
                                        if (newSelection.size >= 2) {
                                            newSelection.removeAt(1)
                                        }
                                        newSelection.add(phone.id)
                                    }
                                    onSelectedChange(newSelection)
                                }
                            )
                        }
                )
            }
        }
    }
}

val phoneItemList = listOf(
    Phone(
        id = 1,
        image = R.drawable.google_pixel_9_,
        title = "Google Pixel 9",
        newPrice = "799",
        display = "6.7",
        mainCamera = "50",
        frontCamera = "12",
        processor = "Tensor G4",
        ram = "8",
        storage = "128",
        battery = "4600"
    ),
    Phone(
        id = 2,
        image = R.drawable.google_pixel_9_pro_,
        title = "Google Pixel 9 Pro",
        newPrice = "999",
        display = "6.3",
        mainCamera = "50",
        frontCamera = "42",
        processor = "Tensor G4",
        ram = "16",
        storage = "256",
        battery = "4700"
    ),
    Phone(
        id = 3,
        image = R.drawable._samsung_galaxy_s24_plus,
        title = "Samsung Galaxy S24+",
        newPrice = "899",
        oldPrice = "999",
        discount = "38",
        display = "6.7",
        mainCamera = "50",
        frontCamera = "12",
        processor = "Snapdragon 8 Gen 3",
        ram = "12",
        storage = "256",
        battery = "4900"
    ),
    Phone(
        id = 4,
        image = R.drawable.oneplus_12,
        title = "OnePlus 12",
        newPrice = "799",
        oldPrice = "899",
        discount = "38",
        display = "6.1",
        mainCamera = "48",
        frontCamera = "32",
        processor = "Snapdragon",
        ram = "12",
        storage = "256",
        battery = "5400"
    ),
    Phone(
        id = 5,
        image = R.drawable._iphone_15_pro,
        title = "iPhone 15 Pro",
        newPrice = "1099",
        display = "6.1",
        mainCamera = "48",
        frontCamera = "12",
        processor = "iOS",
        ram = "8",
        storage = "128",
        battery = "5400"
    ),
    Phone(
        id = 6,
        image = R.drawable.xiaomi_14,
        title = "Xiaomi 14",
        newPrice = "699",
        oldPrice = "799",
        discount = "38",
        display = "6.5",
        mainCamera = "32",
        frontCamera = "32",
        processor = "Snapdragon",
        ram = "8",
        storage = "256",
        battery = "4700"
    )
)

data class Phone(
    val id: Int,
    val image: Int,
    val title: String,
    val newPrice: String,
    val oldPrice: String? = null,
    val discount: String? = null,
    val display: String = "",
    val mainCamera: String = "",
    val frontCamera: String = "",
    val processor: String = "",
    val ram: String = "",
    val storage: String = "",
    val battery: String = ""
)

@Composable
fun PhoneItem(
    phone: Phone,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFFFFFFF))
            .border(
                width = 2.dp,
                color = if (isSelected) Color(0xFF4291FF) else Color(0xFFFFFFFF),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        PhoneItemLayout(
            phone = Phone(
                id = phone.id,
                image = phone.image,
                title = phone.title,
                newPrice = phone.newPrice,
                oldPrice = phone.oldPrice,
                discount = phone.discount
            )
        )
    }
}

@Composable
fun PhoneItemLayout(
    phone: Phone,
    size: Dp = 170.dp,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .wrapContentSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = when (size) {
                        170.dp -> Color.Transparent
                        else -> Color(0xFFD8E4EA)
                    },
                    shape = RoundedCornerShape(4.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(phone.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(size)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFFFFFFFF))
            )
            phone.discount?.let {
                Text(
                    text = " -${phone.discount}% ",
                    fontFamily = hostGroteskFamily,
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    color = Color(0xFFFFFFFF),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 2.dp, end = 6.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(Color(0xFFE6556F))
                )
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = phone.title,
            fontFamily = hostGroteskFamily,
            fontSize = 14.sp,
            lineHeight = 16.sp,
            color = Color(0xFF526881),
            fontWeight = FontWeight.Medium,
            maxLines = 2
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            phone.discount?.let {
                Text(
                    text = "$${phone.newPrice}",
                    fontFamily = hostGroteskFamily,
                    fontSize = 18.sp,
                    lineHeight = 24.sp,
                    color = Color(0xFFE6556F),
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "$${phone.oldPrice}",
                    fontFamily = hostGroteskFamily,
                    fontSize = 14.sp,
                    lineHeight = 16.sp,
                    textDecoration = TextDecoration.LineThrough,
                    color = Color(0xFFA8B7BF),
                    fontWeight = FontWeight.Medium
                )
            } ?: Text(
                text = "$${phone.newPrice}",
                fontFamily = hostGroteskFamily,
                fontSize = 18.sp,
                lineHeight = 24.sp,
                color = Color(0xFF041221),
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(100.dp))
                .background(Color(0xFF4291FF)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Buy",
                fontFamily = hostGroteskFamily,
                fontSize = 14.sp,
                lineHeight = 16.sp,
                color = Color(0xFFFFFFFF),
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(vertical = 6.dp)
            )
        }
    }
}

@Preview
@Composable
private fun PhoneItemPreview() {
    PhoneItem(
        phone = Phone(
            id = 1,
            image = R.drawable.google_pixel_9_,
            title = "Google Pixel 9",
            newPrice = "100",
            oldPrice = "160",
            discount = "38"
        ),
        isSelected = false
    )
}

@Preview
@Composable
private fun LongPressComparePreview() {
    LongPressCompare(
        resultStore = rememberResultStore(),
        onNavigateToComparisonScreen = {},
        selectedPhoneIds = emptyList(),
        onSelectedChange = {}
    )
}