package com.kabi.minichallenge.sept25DesignTheFest

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kabi.minichallenge.R

@Composable
fun ExpandableListScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = Color(0xFFFFFFF0)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = "Festival\nLineup",
                fontFamily = parkinsansFamily,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Normal,
                fontSize = 60.sp,
                lineHeight = 50.sp,
                modifier = Modifier
                    .fillMaxWidth(),
                color = Color(0xFF421E17)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Tap a stage to view performers",
                fontSize = 20.sp,
                fontFamily = parkinsansFamily,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
                modifier = Modifier
                    .fillMaxWidth(),
                color = Color(0xFF786B68)
            )
        }

        val stages = listOf(
            Stage(
                "Stage A",
                Color(0xFFE0E270),
                listOf(
                    Performer("Morning Bloom", "11:00"),
                    Performer("Synth River", "12:30")
                )
            ),
            Stage(
                "Stage B",
                Color(0xFFEC9C50),
                listOf(
                    Performer("The Suntones", "13:00"),
                    Performer("Blue Voltage", "14:15"),
                    Performer("Midnight Echo", "15:30")
                )
            ),
            Stage(
                "Stage C",
                Color(0xFFF59BB0),
                listOf(
                    Performer("Echo Machine ", "16:00"),
                    Performer("The 1975 ", "17:15")
                )
            ),

        )

        var expandedStage by remember { mutableStateOf<String?>(null) }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(stages) { stage ->
                StageCard(
                    stage = stage,
                    expanded = expandedStage == stage.name,
                    onCardClick = {
                        expandedStage = if (expandedStage == stage.name) null else stage.name
                    }
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

val parkinsansFamily = FontFamily(
    Font(R.font.parkinsans_bold, FontWeight.Bold),
    Font(R.font.parkinsans_extrabold, FontWeight.ExtraBold),
    Font(R.font.parkinsans_light, FontWeight.ExtraLight),
    Font(R.font.parkinsans_medium, FontWeight.Medium),
    Font(R.font.parkinsans_regular, FontWeight.Normal),
    Font(R.font.parkinsans_semibold, FontWeight.SemiBold),
)

data class Performer(
    val name: String,
    val time: String
)

data class Stage(
    val name: String,
    val color: Color,
    val performers: List<Performer>
)

@Composable
fun StageCard(
    stage: Stage,
    expanded: Boolean,
    onCardClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = RoundedCornerShape(16.dp),
        onClick = onCardClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = stage.color
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 40.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stage.name,
                fontSize = 38.sp,
                fontFamily = parkinsansFamily,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Normal,
                color = Color(0xFF4A1E1E)
            )
            IconButton(
                onClick = onCardClick,
                modifier = Modifier
                    .size(40.dp)
            ) {
                if (expanded) {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = "Collapse",
                        tint = Color(0xFF4A1E1E),
                        modifier = Modifier
                            .fillMaxSize()
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Expand",
                        tint = Color(0xFF4A1E1E),
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
        }

        if (expanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = stage.color
                    )
            ) {
                stage.performers.forEachIndexed { index, performer ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 16.dp
                            )
                            .padding(
                                top = 14.dp,
                                bottom = 28.dp
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = performer.name,
                            fontSize = 24.sp,
                            fontFamily = parkinsansFamily,
                            fontWeight = FontWeight.Medium,
                            fontStyle = FontStyle.Normal,
                            color = Color(0xFF4A1E1E)
                        )
                        Text(
                            text = performer.time,
                            fontSize = 24.sp,fontFamily = parkinsansFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontStyle = FontStyle.Normal,
                            color = Color(0xFF4A1E1E)
                        )
                    }
                    if (index != stage.performers.lastIndex) {
                        HorizontalDivider(
                            modifier = Modifier
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 6.dp
                                ),
                            thickness = 2.dp,
                            color = Color(0xFF4A1E1E)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ExpandableListScreenPreview() {
    ExpandableListScreen()
}