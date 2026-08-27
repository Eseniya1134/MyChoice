package com.mychoice.presentation.navigation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mychoice.presentation.navigation.model.NavigationBottomModel
import com.mychoice.auth.presentation.authNavGraph
import com.mychoice.presentation.rating.RatingScreen
import com.mychoice.search.presentation.searchGraph
import com.mychoice.settings.presentation.settingsGraph

@Composable
fun NavigationScreen(
    navController: NavHostController = rememberNavController()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    NavHost(
        navController = navController,
        startDestination = "auth"
    ) {
        authNavGraph(
            navController = navController,
            onAuthSuccess = {
                navController.navigate("main") {
                    popUpTo("auth") { inclusive = true }
                }
            }
        )

        composable("main") {
            MainScreen(
                onAuthSuccess = {
                    navController.navigate("auth") {
                        popUpTo("main") { inclusive = true }
                    }
                },
                onLogoutRequested = {  // 👈 ДОБАВЛЯЕМ
                    navController.navigate("auth") {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                snackbarHostState = snackbarHostState
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    onAuthSuccess: () -> Unit,
    onLogoutRequested: () -> Unit,  // 👈 ДОБАВЛЯЕМ
    snackbarHostState: SnackbarHostState
) {
    val mainNavController = rememberNavController()

    Scaffold(
        bottomBar = { AppBottomBar(mainNavController) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NavHost(
                navController = mainNavController,
                startDestination = NavigationBottomModel.News.route
            ) {
                composable(NavigationBottomModel.News.route) { NewsNavigationScreen() }
                composable(NavigationBottomModel.Rating.route) { RatingNavigationScreen() }
                composable(NavigationBottomModel.Discussions.route) { DiscussionsNavigationScreen() }

                settingsGraph(
                    navController = mainNavController,
                    onAuthSuccess = onAuthSuccess,
                    onLogoutRequested = onLogoutRequested,  // 👈 ПЕРЕДАЕМ
                    snackbarHostState = snackbarHostState
                )

                searchGraph(mainNavController)
            }
        }
    }
}

@Composable
fun RatingNavigationScreen() {
    RatingScreen()
}

@Composable
fun DiscussionsNavigationScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Дискуссии")
    }
}

@Composable
fun NewsNavigationScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Новости")
    }
}