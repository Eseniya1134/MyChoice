package com.mychoice.presentation.rating

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mychoice.resources.R

// Модель строки рейтинга
data class RatingEntry(
    val rank: Int,
    val universityName: String,
    val score: Double,
    val delta: Double,
    val isPositive: Boolean
)

// Временные тестовые данные (не кликабельно, без ViewModel)
private val sampleEntries = listOf(
    RatingEntry(1, "МГУ им. Ломоносова", 98.5, 0.2, true),
    RatingEntry(2, "МФТИ", 97.8, 0.5, true),
    RatingEntry(3, "НИУ ВШЭ", 96.4, 0.1, true),
    RatingEntry(4, "СПбГУ", 95.1, -0.3, false),
    RatingEntry(5, "ИТМО", 94.7, 0.4, true)
)

@Composable
fun RatingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        RatingHeader()
        Spacer(Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.rating_update_description),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 4.dp, bottom = 12.dp)
        ) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(sampleEntries, key = { it.rank }) { entry ->
                    RatingRow(entry = entry)
                }
            }
        }
    }
}

// Градиентная шапка (в стиле SearchHeader / LoginHeader)
@Composable
private fun RatingHeader() {
    val gradientColors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.primaryContainer
    )

    Card(
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = gradientColors,
                        start = androidx.compose.ui.geometry.Offset(0f, 0f),
                        end = androidx.compose.ui.geometry.Offset(
                            Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY
                        )
                    )
                )
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Text(
                text = stringResource(R.string.rating_screen_title),
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )
        }
    }
}

// Строка рейтинга — не кликабельная
@Composable
private fun RatingRow(entry: RatingEntry) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Номер места в цветном кружке
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(rankColor(entry.rank).copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${entry.rank}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = rankColor(entry.rank)
                )
            }

            Spacer(Modifier.width(12.dp))

            Text(
                text = entry.universityName,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )

            Spacer(Modifier.width(8.dp))

            Text(
                text = entry.score.toString(),
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(Modifier.width(12.dp))

            // Динамика
            val deltaColor = if (entry.isPositive)
                Color(0xFF4CAF50)
            else
                MaterialTheme.colorScheme.error

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (entry.isPositive) Icons.Default.TrendingUp
                    else Icons.Default.TrendingDown,
                    contentDescription = stringResource(R.string.rating_star_content_description),
                    tint = deltaColor,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(Modifier.width(2.dp))
                Text(
                    text = "${if (entry.isPositive) "+" else ""}${entry.delta}",
                    fontSize = 13.sp,
                    color = deltaColor
                )
            }
        }
    }
}

// Цвет для топ-3 мест (золото/серебро/бронза), остальные — стандартный primary
@Composable
private fun rankColor(rank: Int): Color = when (rank) {
    1 -> Color(0xFFFFC107)
    2 -> Color(0xFFB0BEC5)
    3 -> Color(0xFFCD7F32)
    else -> MaterialTheme.colorScheme.primary
}

@Preview(showBackground = true)
@Composable
private fun RatingScreenPreview() {
    RatingScreen()
}