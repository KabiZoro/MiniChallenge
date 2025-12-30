@file:OptIn(ExperimentalMaterial3Api::class)

package com.kabi.minichallenge.nov25BlackFriday

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kabi.minichallenge.R
import java.util.Locale

object LocaleHelper {
    fun updateAppLocale(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val resources = context.resources
        val configuration = Configuration(resources.configuration)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(locale)
            context.createConfigurationContext(configuration)
        } else {
            configuration.locale = locale
            resources.updateConfiguration(configuration, resources.displayMetrics)
            context
        }
    }
}

@Composable
fun GlobalBlackFridayDeals(modifier: Modifier = Modifier) {

    val base = LocalContext.current

    val sharedPrefs = remember {
        base.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    var lang by remember {
        mutableStateOf(sharedPrefs.getString("selected_lang", "en") ?: "en")
    }

    val localizedContext = remember(lang) {
        LocaleHelper.updateAppLocale(base, lang)
    }

    var isDropDownExpanded by remember {
        mutableStateOf(false)
    }

    val layoutDirection = if (lang == "ar") LayoutDirection.Rtl else LayoutDirection.Ltr

    CompositionLocalProvider(
        LocalContext provides localizedContext,
        LocalLayoutDirection provides layoutDirection
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                painter = if (lang == "en") painterResource(R.drawable.arrow_left)
                                else painterResource(R.drawable.arrow_right),
                                contentDescription = null,
                                tint = Color(0xFF211304),
                                modifier = Modifier
                                    .size(20.dp)
                            )
                        }
                    },
                    title = {
                        Text(
                            text = "SALE",
                            fontFamily = hostGroteskFamily,
                            fontSize = 22.sp,
                            color = Color(0xFF211304),
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 2
                        )
                    },
                    actions = {
                        Row {
                            IconButton(
                                onClick = {}
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.shopping_cart),
                                    contentDescription = null,
                                    tint = Color(0xFF211304),
                                    modifier = Modifier
                                        .size(20.dp)
                                )
                            }

                            Box {
                                IconButton(
                                    onClick = { isDropDownExpanded = true }
                                ) {
                                    Image(
                                        painter = if (lang == "en") painterResource(R.drawable.english)
                                        else if (lang == "sp") painterResource(R.drawable.spanish)
                                        else painterResource(R.drawable.arab),
                                        contentDescription = "Change Language",
                                        modifier = Modifier.size(28.dp)
                                    )
                                }

                                DropdownMenu(
                                    expanded = isDropDownExpanded,
                                    onDismissRequest = { isDropDownExpanded = false },
                                    modifier = Modifier.background(Color(0xFFF6F2ED))
                                ) {

                                    val onLanguageChange: (String) -> Unit = { selectedLang ->
                                        lang = selectedLang
                                        isDropDownExpanded = false
                                        sharedPrefs.edit()
                                            .putString("selected_lang", selectedLang)
                                            .apply()
                                    }

                                    if (lang != "en") {
                                        DropdownMenuItem(
                                            modifier = Modifier
                                                .padding(end = 8.dp),
                                            text = {
                                                Text(
                                                    text = "English",
                                                    fontFamily = hostGroteskFamily,
                                                    fontSize = 16.sp,
                                                    color = Color(0xFF211304),
                                                    fontWeight = FontWeight.Medium
                                                )
                                            },
                                            onClick = { onLanguageChange("en") },
                                            leadingIcon = {
                                                Image(
                                                    painterResource(R.drawable.english),
                                                    contentDescription = null,
                                                    modifier = Modifier.size(20.dp)
                                                )
                                            }
                                        )
                                    }
                                    if (lang != "sp") {
                                        DropdownMenuItem(
                                            modifier = Modifier
                                                .padding(end = 8.dp),
                                            text = {
                                                Text(
                                                    text = "Español",
                                                    fontFamily = hostGroteskFamily,
                                                    fontSize = 16.sp,
                                                    color = Color(0xFF211304),
                                                    fontWeight = FontWeight.Medium
                                                )
                                            },
                                            onClick = { onLanguageChange("sp") },
                                            leadingIcon = {
                                                Image(
                                                    painterResource(R.drawable.spanish),
                                                    contentDescription = null,
                                                    modifier = Modifier.size(20.dp)
                                                )
                                            }
                                        )
                                    }
                                    if (lang != "ar") {
                                        DropdownMenuItem(
                                            modifier = Modifier
                                                .padding(end = 8.dp),
                                            text = {
                                                Text(
                                                    text = "العربية",
                                                    fontFamily = hostGroteskFamily,
                                                    fontSize = 16.sp,
                                                    color = Color(0xFF211304),
                                                    fontWeight = FontWeight.Medium
                                                )
                                            },
                                            onClick = { onLanguageChange("ar") },
                                            leadingIcon = {
                                                Image(
                                                    painterResource(R.drawable.arab),
                                                    contentDescription = null,
                                                    modifier = Modifier.size(20.dp)
                                                )
                                            }
                                        )
                                    }
                                }
                            }

                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFFF6F2ED)
                    )
                )
            },
            containerColor = Color(0xFFF6F2ED),
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
                items(productItemsList) { productItem ->
                    ProductItemCard(
                        productItem = productItem
                    )
                }
            }
        }
    }
}

val productItemsList = listOf(
    ProductItem(
        image = R.drawable.product_1,
        title = R.string.guess_vincent_sneakers,
        newPrice = "100",
        oldPrice = "160",
        discount = "38"
    ),
    ProductItem(
        image = R.drawable.product_2,
        title = R.string.armani_t_shirt,
        newPrice = "46",
        oldPrice = "57",
        discount = "19"
    ),
    ProductItem(
        image = R.drawable.product_3,
        title = R.string.hoodie_with_shirt,
        newPrice = "100",
        oldPrice = "160",
        discount = "38"
    ),
    ProductItem(
        image = R.drawable.product_4,
        title = R.string.gant_cotton_t_shirt,
        newPrice = "46",
        oldPrice = "57",
        discount = "19"
    ),
    ProductItem(
        image = R.drawable.product_5,
        title = R.string.united_colors_of_benetton_cotton_shirt,
        newPrice = "20",
        oldPrice = "40",
        discount = "33"
    ),
    ProductItem(
        image = R.drawable.product_6,
        title = R.string.armani_exchange_t_shirt_2_pack,
        newPrice = "20",
        oldPrice = "40",
        discount = "33"
    ),
    ProductItem(
        image = R.drawable.product_7,
        title = R.string.armani_exchange_t_shirt,
        newPrice = "35",
        oldPrice = "81",
        discount = "56"
    ),
    ProductItem(
        image = R.drawable.product_8,
        title = R.string.daniel_wellington,
        newPrice = "100",
        oldPrice = "160",
        discount = "38"
    )
)

data class ProductItem(
    val image: Int = R.drawable.product_1,
    val title: Int = R.string.guess_vincent_sneakers,
    val newPrice: String = "100",
    val oldPrice: String = "160",
    val discount: String = "38"
)

@Composable
fun ProductItemCard(
    modifier: Modifier = Modifier,
    productItem: ProductItem = ProductItem(),
) {
    Column(
        modifier = modifier
            .width(160.dp)
            .background(Color.White)
            .padding(4.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .background(Color(0xFFF7F6F4))
        ) {

            Image(
                painter = painterResource(productItem.image),
                contentDescription = null,
                modifier = Modifier
                    .height(218.dp),
                contentScale = ContentScale.Crop
            )

            Icon(
                painter = painterResource(R.drawable.heart),
                contentDescription = "Favorite",
                tint = Color(0xFF9A9795),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(18.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 8.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = "\u200E-${productItem.discount}%",
                    fontFamily = hostGroteskFamily,
                    fontSize = 12.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .background(Color(0xFF7C1414))
                        .padding(start = 8.dp, end = 4.dp)
                        .padding(vertical = 2.dp)
                )
            }

        }

        Spacer(Modifier.height(6.dp))

        Text(
            text = stringResource(productItem.title),
            fontFamily = hostGroteskFamily,
            fontSize = 14.sp,
            color = Color(0xFF211304),
            fontWeight = FontWeight.Medium,
            maxLines = 2
        )

        Spacer(Modifier.height(2.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$${productItem.newPrice}",
                fontFamily = hostGroteskFamily,
                fontSize = 18.sp,
                color = Color(0xFF7C1414),
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.width(6.dp))
            Text(
                text = "$${productItem.oldPrice}",
                fontFamily = hostGroteskFamily,
                fontSize = 14.sp,
                textDecoration = TextDecoration.LineThrough,
                color = Color(0xFF9A9795),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview
@Composable
private fun ProductItemCardPreview() {
    ProductItemCard()
}

@Preview
@Composable
private fun GlobalBlackFridayDealsPreview() {
    GlobalBlackFridayDeals()
}