@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)

package com.kabi.minichallenge.sept25DesignTheFest

import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


// Artist Data

data class ArtistData(
    val category: String,
    val artist: String,
    val time: String,
    val stage: String,
    val description: String
)


// List of Artist Data

private val artistsData = listOf(
    ArtistData(
        category = "Indie",
        artist = "Bon Iver",
        time = "15:30",
        stage = "Main Stage",
        description = "Atmospheric folk-electronic set to start the day."
    ),
    ArtistData(
        category = "Electronic",
        artist = "Jamie xx",
        time = "16:00",
        stage = "Electro Stage",
        description = "A genre-bending solo set with deep bass and light textures."
    ),
    ArtistData(
        category = "Headliner",
        artist = "Florence + The Machine",
        time = "17:00",
        stage = "Main Stage",
        description = "Special acoustic set this evening only."
    ),
    ArtistData(
        category = "Indie",
        artist = "The National",
        time = "18:00",
        stage = "Sunset Stage",
        description = "Known for their emotional rock anthems."
    ),
    ArtistData(
        category = "Experimental",
        artist = "Björk",
        time = "18:30",
        stage = "Electro Stage",
        description = "Avant-garde visual and vocal per formance."
    ),
    ArtistData(
        category = "Indie",
        artist = "Tame Impala",
        time = "19:00",
        stage = "Sunset Stage",
        description = "Celebrated psychedelic show from Australia."
    ),
    ArtistData(
        category = "Electronic",
        artist = "The Chemical Brothers",
        time = "20:15",
        stage = "Electro Stage",
        description = "High-energy visuals with legendary electronica."
    ),
    ArtistData(
        category = "Headliner",
        artist = "Foo Fighters",
        time = "21:00",
        stage = "Main Stage",
        description = "Classic stadium rock at its finest."
    ),
    ArtistData(
        category = "Alt Rock",
        artist = "Arctic Monkeys",
        time = "22:00",
        stage = "Sunset Stage",
        description = "Charismatic blend of indie rock and post-punk revival."
    ),
    ArtistData(
        category = "Headliner",
        artist = "Radiohead",
        time = "23:00",
        stage = "Main Stage",
        description = "Returning to t he stage wit h a new album."
    )
)

@Composable
fun AccessibilityScreen(
    modifier: Modifier = Modifier,
    items: List<ArtistData> = artistsData
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Schedule",
                        color = Color(0xFF421E17),
                        fontSize = 44.sp,
                        fontFamily = parkinsansFamily,
                        fontWeight = FontWeight.SemiBold,
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(items.size) { index ->
                AccessibilityItem(item = items[index])
            }
        }
    }
}

@Preview
@Composable
private fun AccessibilityScreenPreview() {
    AccessibilityScreen()
}


// Accessibility Item

@Composable
fun AccessibilityItem(item: ArtistData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFCF3E2)
        )
    ) {
        val containerColor = when (item.category) {
            "Indie" -> Color(0xFFEC9C50)
            "Electronic" -> Color(0xFFE0E270)
            "Headliner" -> Color(0xFFEEB7FA)
            "Experimental" -> Color(0xFFF59BB0)
            else -> Color(0xFF86DDF8)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clearAndSetSemantics {
                    contentDescription =
                        "${item.artist}. Playing at ${item.time} on the ${item.stage}. Category: ${item.category}. Description: ${item.description}"
                }
                .padding(
                    horizontal = 16.dp,
                    vertical = 20.dp
                )
        ) {
            Chip(
                text = item.category,
                containerColor = containerColor
            )
            Spacer(modifier = Modifier.height(20.dp))
            ArtistTextMarquee(
                artist = item.artist,
                time = item.time,
                stage = item.stage
            )
            Spacer(modifier = Modifier.height(10.dp))
            ArtistDescription(
                description = item.description
            )
        }
    }
}

@Preview
@Composable
private fun AccessibilityItemPreview() {
    AccessibilityItem(
        ArtistData(
            category = "Alt Rock",
            artist = "Arctic Monkeys",
            time = "22:00",
            stage = "Sunset Stage",
            description = "Charismatic blend of indie rock and post-punk revival."
        )
    )
}


// Chip

@Composable
fun Chip(
    text: String,
    containerColor: Color
) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(
                color = containerColor,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp
            )
            .semantics { invisibleToUser() }
    ) {
        Text(
            text = text,
            color = Color(0xFF421E17),
            fontSize = 14.sp,
            fontFamily = parkinsansFamily,
            fontWeight = FontWeight.Medium,
            fontStyle = FontStyle.Normal
        )
    }
}

@Preview
@Composable
private fun ChipPreview() {
    Chip(
        text = "Indie",
        containerColor = Color(0xFFEC9C50)
    )
}


// Artist Text Marquee

@Composable
fun ArtistTextMarquee(
    artist: String,
    time: String,
    stage: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .basicMarquee(
                iterations = 2,
                animationMode = MarqueeAnimationMode.Immediately,
                initialDelayMillis = 1000,
                repeatDelayMillis = 3000,
                velocity = 40.dp
            )
            .semantics { invisibleToUser() },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = artist,
            fontSize = 19.sp,
            fontFamily = parkinsansFamily,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            maxLines = 1
        )
        Spacer(modifier = Modifier.width(24.dp))
        Text(
            text = "$time • $stage",
            fontSize = 19.sp,
            fontFamily = parkinsansFamily,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Normal,
            maxLines = 1
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ArtistTextMarqueePreview() {
    ArtistTextMarquee(
        artist = "Artist",
        time = "15:30",
        stage = "Stage A"
    )
}


// Artist Description

@Composable
fun ArtistDescription(
    description: String
) {
    Text(
        text = description,
        color = Color(0xFF786B68),
        fontSize = 14.sp,
        fontFamily = parkinsansFamily,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Normal,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .fillMaxWidth()
            .semantics { invisibleToUser() }
    )
}

@Preview(showBackground = true)
@Composable
private fun ArtistDescriptionPreview() {
    ArtistDescription(
        description = "Artist Description"
    )
}