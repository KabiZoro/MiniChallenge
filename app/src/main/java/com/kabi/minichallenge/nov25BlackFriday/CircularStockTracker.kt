package com.kabi.minichallenge.nov25BlackFriday

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kabi.minichallenge.R
import com.kabi.minichallenge.june25BdayCeleb.maliFamily

@Composable
fun StockTrackerScreen(modifier: Modifier = Modifier) {

    var count by remember { mutableStateOf(12) }
    var size by remember { mutableStateOf(42) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(16.dp)
                )
                .size(360.dp, 560.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = if (count > 0){
                            listOf(
                                Color(0xFFD33F3F),
                                Color(0xFF7C1414)
                            )
                        }else {
                            listOf(
                                Color(0xFFDFDDDB),
                                Color(0xFFDFDDDB)
                            )
                        }
                    )
                )
        ) {
            Image(
                painter = painterResource(R.drawable.shoe),
                contentDescription = null
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFFFFF)
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    // shoe name row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Nike Air Zoom Pegasus 41",
                                color = Color(0xFF211304),
                                style = MaterialTheme.typography.bodyLarge,
                                fontFamily = hostGroteskFamily,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Text(
                                text = "Legendary running shoes with Air Zoom technology and ReactX cushioning for daily training and marathons.",
                                color = Color(0xFF9A9795),
                                style = MaterialTheme.typography.bodySmall,
                                fontFamily = hostGroteskFamily,
                                fontWeight = FontWeight.Medium,
                            )
                        }
                        Column(
                            modifier = Modifier
                                .padding(start = 8.dp),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = "$100",
                                color = Color(0xFF7C1414),
                                style = MaterialTheme.typography.headlineSmall,
                                fontFamily = hostGroteskFamily,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Text(
                                text = "$160",
                                color = Color(0xFF9A9795),
                                style = MaterialTheme.typography.bodySmall,
                                fontFamily = hostGroteskFamily,
                                fontWeight = FontWeight.Medium,
                                textDecoration = TextDecoration.LineThrough
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // size row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                        ) {
                            Text(
                                text = "Choose your size",
                                color = Color(0xFF9A9795),
                                style = MaterialTheme.typography.bodySmall,
                                fontFamily = hostGroteskFamily,
                                fontWeight = FontWeight.Medium,
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                for (i in 39..44) {
                                    val isSelected = size == i
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .size(32.dp)
                                            .clip(RoundedCornerShape(6.dp))
                                            .border(
                                                width = 1.dp,
                                                color = Color(0xFFDFDDDB)
                                            )
                                            .background(
                                                if (isSelected) {
                                                    Color(0xFF6B5D4F)
                                                } else {
                                                    Color(0xFFFFFFFF)
                                                }
                                            )
                                            .clickable {
                                                size = i
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = i.toString(),
                                            color = if (isSelected) {
                                                Color(0xFFFFFFFF)
                                            } else {
                                                Color(0xFF6B5D4F)
                                            },
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontFamily = hostGroteskFamily,
                                            fontWeight = FontWeight.Medium,
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // remaining row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val maxItem = 100
                        val progress = count.toFloat() / maxItem.toFloat()
                        val animatedProgress by animateFloatAsState(
                            targetValue = progress,
                            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
                            label = "Stock Progress"
                        )

                        Box(
                            modifier = Modifier
                                .size(36.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                progress = { maxItem.toFloat() },
                                strokeWidth = 2.dp,
                                trackColor = Color(0xFFDFDDDB),
                                color = Color(0xFFDFDDDB)
                            )
                            CircularProgressIndicator(
                                progress = { animatedProgress },
                                modifier = Modifier.fillMaxSize(),
                                strokeWidth = 2.dp,
                                strokeCap = StrokeCap.Round,
                                color = Color(0xFF7C1414),
                                trackColor = Color(0xFFDFDDDB)
                            )
                            AnimatedContent(
                                targetState = count,
                                label = "Countdown Text Animation",
                                transitionSpec = {
                                    scaleIn(initialScale = 2f) + fadeIn() togetherWith
                                            scaleOut(targetScale = 1f) + fadeOut()
                                }
                            ) {
                                Text(
                                    text = it.toString(),
                                    color = Color(0xFF211304),
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontFamily = hostGroteskFamily,
                                    fontWeight = FontWeight.SemiBold,
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Remaining at discounted price",
                            color = Color(0xFF9A9795),
                            style = MaterialTheme.typography.bodySmall,
                            fontFamily = hostGroteskFamily,
                            fontWeight = FontWeight.Medium,
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // button
                    Button(
                        onClick = {
                            count -= 1
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        shape = RoundedCornerShape(8.dp),
                        enabled = if (count > 0) true else false,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (count > 0) Color(0xFF211304)
                            else Color(0xFFDFDDDB),
                            contentColor = Color(0xFFFFFFFF)
                        )
                    ) {
                        Text(
                            text = if (count > 0) "Buy"
                            else "Out of Stock",
                            style = MaterialTheme.typography.bodyMedium,
                            fontFamily = hostGroteskFamily,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun StockTrackerScreenPreview() {
    StockTrackerScreen()
}