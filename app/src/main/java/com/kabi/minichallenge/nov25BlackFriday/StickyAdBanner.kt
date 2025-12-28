@file:OptIn(ExperimentalMaterial3Api::class)

package com.kabi.minichallenge.nov25BlackFriday

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kabi.minichallenge.R

// banner

@Composable
fun AdBanner(
    isSticky: Boolean = false,
    onDismiss: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(
                horizontal = if (isSticky) 0.dp else 4.dp,
                vertical = if (isSticky) 0.dp else 8.dp
            )
            .clip(RoundedCornerShape(if (isSticky) 0.dp else 16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.banner_bg),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.End
            ) {
                Box(
                    modifier = Modifier
                        .clickable {
                            onDismiss()
                        }
                        .padding(top = 8.dp, end = 8.dp)
                        .size(20.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.x),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
            Text(
                text = "Black Friday Deals",
                color = Color(0xFFFFFFFF).copy(0.7f),
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = hostGroteskFamily,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(start = 24.dp)
            )
            Text(
                text = "-50%",
                color = Color(0xFFFFFFFF),
                fontSize = 40.sp,
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = hostGroteskFamily,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 24.dp)
            )
        }

    }
}

@Preview
@Composable
private fun AdBannerPreview() {
    AdBanner(
        isSticky = true,
        onDismiss = {}
    )
}

// product

data class Product(
    val id: Int,
    val name: String,
    val price: String,
    val imageRes: Int
)

// product list

val productList = listOf(
    Product(
        id = 1,
        name = "Google Pixel 9 Pro",
        price = "999",
        imageRes = R.drawable.google_pixel_9_pro
    ),
    Product(
        id = 2,
        name = "Google Pixel 9",
        price = "799",
        imageRes = R.drawable.google_pixel_9
    ),
    Product(
        id = 3,
        name = "Google Pixel 8 Pro",
        price = "899",
        imageRes = R.drawable.google_pixel_8_pro
    ),
    Product(
        id = 4,
        name = "Google Pixel 8",
        price = "699",
        imageRes = R.drawable.google_pixel_8
    ),
    Product(
        id = 5,
        name = "Google Pixel 9 Pro",
        price = "999",
        imageRes = R.drawable.google_pixel_9_pro
    ),
    Product(
        id = 6,
        name = "Google Pixel Fold",
        price = "1799",
        imageRes = R.drawable.google_pixel_fold
    ),
    Product(
        id = 7,
        name = "Google Pixel Tablet",
        price = "499",
        imageRes = R.drawable.google_pixel_tablet
    ),
    Product(
        id = 8,
        name = "Google Pixel Watch 2",
        price = "399",
        imageRes = R.drawable.google_pixel_watch_2
    ),
    Product(
        id = 9,
        name = "Google Pixel Buds Pro",
        price = "199",
        imageRes = R.drawable.google_pixel_buds_pro
    ),
    Product(
        id = 10,
        name = "Google Nest Hub (2nd Gen)",
        price = "99",
        imageRes = R.drawable.google_nest_hub__2nd_gen_
    ),
    Product(
        id = 11,
        name = "Google Nest Audio",
        price = "99",
        imageRes = R.drawable.google_nest_audio
    )
)

// product card

@Composable
fun ProductItemCard(product: Product) {
    Box(
        modifier = Modifier
            .height(IntrinsicSize.Max)
            .width(IntrinsicSize.Max)
            .background(
                Color(0xFFFFFFFF)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(170.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = product.name,
                color = Color(0xFF526881),
                style = MaterialTheme.typography.bodySmall,
                fontFamily = hostGroteskFamily,
                fontWeight = FontWeight.Medium,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$${product.price}",
                color = Color(0xFF041221),
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = hostGroteskFamily,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Preview
@Composable
private fun ProductCardPreview() {
    ProductItemCard(
        product = Product(
            id = 1,
            name = "Google next Hub (2nd Gen)",
            price = "99",
            imageRes = R.drawable.google_pixel_9_pro
        )
    )
}

// product grid

@Composable
fun ProductGrid(
    products: List<Product>,
    isBannerDismissed: Boolean,
    onDismissBanner: () -> Unit,
    gridState: LazyGridState
) {
    val gridItems = remember(products, isBannerDismissed) {
        val items = mutableListOf<Any>()
        products.forEachIndexed { index, product ->
            if (index == 2 && !isBannerDismissed) {
                items.add("BANNER")
            }
            items.add(product)
        }
        items.toList()
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = gridState,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        items(
            items = gridItems,
            key = { item ->
                when (item) {
                    is Product -> item.id
                    is String -> "BANNER"
                    else -> item.hashCode()
                }
            },
            span = { item ->
                when (item) {
                    is Product -> GridItemSpan(1)
                    is String -> GridItemSpan(2)
                    else -> GridItemSpan(1)
                }
            }
        ) { item ->
            when (item) {
                is Product -> ProductItemCard(product = item)
                is String -> AdBanner(
                    onDismiss = onDismissBanner,
                    isSticky = false
                )
            }
        }
    }
}

// product main screen

@Composable
fun ProductMainScreen(modifier: Modifier = Modifier) {
    var isBannerDismissed by remember { mutableStateOf(false) }
    val gridState = rememberLazyGridState()
    val isBannerSticky by remember {
        derivedStateOf {
            if (isBannerDismissed) {
                false
            } else {
                gridState.firstVisibleItemIndex >= 2
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "TechDeals",
                        color = Color(0xFF041221),
                        style = MaterialTheme.typography.headlineMedium,
                        fontFamily = hostGroteskFamily,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(R.drawable.menu),
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(R.drawable.shopping_cart),
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(paddingValues)
        ) {
            ProductGrid(
                products = productList,
                isBannerDismissed = isBannerDismissed,
                onDismissBanner = { isBannerDismissed = true },
                gridState = gridState,
            )

            AnimatedVisibility(
                visible = isBannerSticky,
                enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
                modifier = Modifier.fillMaxWidth()
            ) {
                AdBanner(
                    isSticky = true,
                    onDismiss = { isBannerDismissed = true }
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProductMainScreenPreview() {
    ProductMainScreen()
}


