package com.kabi.minichallenge.sept25DesignTheFest

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DynamicUpdateScreen(modifier: Modifier = Modifier) {
    val ticketOption = listOf(
        TicketType("Standard", 40),
        TicketType("VIP", 70),
        TicketType("Backstage", 120)
    )

    var selectedTicket by remember { mutableStateOf<TicketType?>(null) }
    var quantity by remember { mutableIntStateOf(1) }

    val total = (selectedTicket?.price ?: 0) * quantity
    val isPurchaseEnabled = selectedTicket != null && total > 0

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
                text = "Ticket\nBuilder",
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
                text = "Select Ticket Type & Quantity",
                fontSize = 20.sp,
                fontFamily = parkinsansFamily,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Normal,
                modifier = Modifier
                    .fillMaxWidth(),
                color = Color(0xFF786B68)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Ticket Type Card
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFCF3E2)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "TICKET TYPE:",
                fontSize = 20.sp,
                fontFamily = parkinsansFamily,
                fontWeight = FontWeight.Medium,
                fontStyle = FontStyle.Normal,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 40.dp,
                        bottom = 28.dp
                    )
                    .padding(horizontal = 16.dp),
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                items(ticketOption) { option ->
                    TicketTypeCard(
                        ticket = option,
                        selected = selectedTicket == option,
                        onSelect = { selectedTicket = option }
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            Quantity(
                quantity = quantity,
                onIncrement = { quantity++ },
                onDecrement = { if (quantity > 1) quantity-- }
            )

            Spacer(modifier = Modifier.height(48.dp))

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                thickness = 2.dp,
                color = Color(0xFF4E342E)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 40.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Total:",
                    color = Color(0xFF4E342E),
                    fontSize = 28.sp,
                    fontFamily = parkinsansFamily,
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Normal
                )
                Text(
                    "$${total}",
                    color = Color(0xFF4E342E),
                    fontSize = 28.sp,
                    fontFamily = parkinsansFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Normal
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        PurchaseCard(
            enabled = isPurchaseEnabled,
            onClick = { /* TODO: Handle purchase logic */ }
        )

    }
}

data class TicketType(
    val name: String,
    val price: Int
)

@Composable
fun TicketTypeCard(
    ticket: TicketType,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(
                selectedColor = Color(0xFF4E342E)
            ),
            modifier = Modifier
                .size(24.dp)
        )
        Text(
            text = ticket.name,
            fontSize = 24.sp,
            fontFamily = parkinsansFamily,
            fontWeight = FontWeight.Medium,
            fontStyle = FontStyle.Normal,
            color = Color(0xFF4E342E),
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
                .clickable { onSelect() }
        )

        Text(
            text = "$${ticket.price}",
            fontSize = 24.sp,
            fontFamily = parkinsansFamily,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Normal,
            color = Color(0xFF4E342E),
            modifier = Modifier
                .clickable { onSelect() }
        )
    }
}

@Composable
fun Quantity(
    quantity: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "QUANTITY:",
            fontSize = 20.sp,
            fontFamily = parkinsansFamily,
            fontWeight = FontWeight.Medium,
            fontStyle = FontStyle.Normal
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                modifier = Modifier
                    .size(68.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        color = if (quantity > 1) Color(0xFFFFFFF0)
                        else Color(0xFFFCF3E2)
                    ),
                onClick = onDecrement
            ) {
                Icon(
                    modifier = Modifier
                        .size(40.dp),
                    imageVector = Icons.Default.Remove,
                    contentDescription = null,
                )
            }
            Text(
                text = "$quantity",
                fontSize = 32.sp,
                fontFamily = parkinsansFamily,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Normal
            )
            IconButton(
                modifier = Modifier
                    .size(68.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        color = Color(0xFFFFFFF0)
                    ),
                onClick = onIncrement
            ) {
                Icon(
                    modifier = Modifier
                        .size(40.dp),
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
fun TotalCard(total: Int) {
    Column {
        HorizontalDivider(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 6.dp
                ),
            thickness = 2.dp,
            color = Color(0xFF4A1E1E)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Total:")
            Text(text = "$$total")
        }

    }
}

@Composable
fun PurchaseCard(
    enabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled)
                Color(0xFFE0E270) else Color(0xFFFCF3E2),
            contentColor = if (enabled)
                Color(0xFF421E17) else Color(0xFFA99E9C)
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Purchase",
            fontSize = 30.sp,
            fontFamily = parkinsansFamily,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Normal,
            modifier = Modifier
                .padding(vertical = 25.dp)
        )
    }
}


@Preview
@Composable
private fun DynamicUpdateScreenPreview() {
    DynamicUpdateScreen()
}