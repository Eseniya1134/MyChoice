package com.mychoice.presentation.navigation.screen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.games.leaderboard.Leaderboard
import com.mychoice.presentation.navigation.model.NavigationBottomModel


@Composable
fun AppBottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val barColor = MaterialTheme.colorScheme.primary
    val unselectedIconColor = MaterialTheme.colorScheme.onPrimary
    val selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer

    val navIcons = mapOf(
        NavigationBottomModel.News.route to Icons.Default.Article,
        NavigationBottomModel.Search.route to Icons.Default.Search,
        NavigationBottomModel.Rating.route to Icons.Default.Leaderboard,
        NavigationBottomModel.Discussions.route to Icons.Default.Forum,
        NavigationBottomModel.Settings.route to Icons.Default.Settings
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {

        //  ФОН НАВИГАЦИИ (отдельный слой)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(50))
                .background(barColor)
        )

        // ИКОНКИ (поверх)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavigationBottomModel.getNavItems().forEach { item ->
                val isSelected = currentRoute == item.route

                val offsetY by animateDpAsState(
                    targetValue = if (isSelected) (-24).dp else 0.dp,
                    animationSpec = tween(300),
                    label = ""
                )

                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .offset(y = offsetY)
                        .clip(CircleShape)
                        .background(if (isSelected) barColor else Color.Transparent)
                        .border(
                            width = if (isSelected) 4.dp else 0.dp,
                            color = if (isSelected) unselectedIconColor else Color.Transparent,
                            shape = CircleShape
                        )
                        .clickable {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = navIcons[item.route] ?: Icons.Default.Circle,
                        contentDescription = item.text,
                        tint = if (isSelected) selectedIconColor else unselectedIconColor,
                        modifier = Modifier.size(26.dp)
                    )
                }


            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppBottomBarPreview() {
    AppBottomBar(navController = rememberNavController())
}