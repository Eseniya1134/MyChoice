package com.mychoice.presentation.navigation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.mychoice.presentation.navigation.model.NavigationBottomModel
import com.mychoice.auth.presentation.authNavGraph
import com.mychoice.presentation.rating.RatingScreen
import com.mychoice.search.presentation.searchGraph
import com.mychoice.settings.presentation.settingsGraph

@Composable
fun NavigationScreen(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "auth"
        //startDestination = "main"
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
            MainScreen()
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = { AppBottomBar(navController) }
    ) {
        NavHost(
            navController = navController,
            startDestination = "search_graph"
        ) {
            //composable(NavigationBottomModel.Search.route) { SearchNavigationScreen() }
            composable(NavigationBottomModel.News.route) { NewsNavigationScreen() }
            composable(NavigationBottomModel.Rating.route) { RatingNavigationScreen() }
            composable(NavigationBottomModel.Discussions.route) { DiscussionsNavigationScreen() }
            settingsGraph(navController)
            searchGraph(navController)
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

@Composable
fun SearchNavigationScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Вузы")
    }
}