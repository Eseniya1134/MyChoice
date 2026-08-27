// presentation/AuthNavGraph.kt
package com.mychoice.auth.presentation

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

object AuthRoutes {
    const val GRAPH    = "auth"
    const val LOGIN    = "auth/login"
    const val REGISTER = "auth/register"
}

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    onAuthSuccess: () -> Unit
) {
    navigation(
        startDestination = AuthRoutes.LOGIN,
        route = AuthRoutes.GRAPH
    ) {

        composable(AuthRoutes.LOGIN) {
            val snackbarHostState = remember { SnackbarHostState() }

            val justRegistered = navController
                .currentBackStackEntry
                ?.savedStateHandle
                ?.get<Boolean>("just_registered") ?: false

            LaunchedEffect(justRegistered) {
                if (justRegistered) {
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("just_registered", false)
                    snackbarHostState.showSnackbar(
                        message  = "Регистрация прошла успешно! Теперь войдите.",
                        duration = SnackbarDuration.Short
                    )
                }
            }

            LoginScreen(
                onLoginSuccess = onAuthSuccess,
                onGoToRegister = {
                    navController.navigate(AuthRoutes.REGISTER) {
                        launchSingleTop = true
                    }
                },
                snackbarHostState = snackbarHostState
            )
        }

        composable(AuthRoutes.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("just_registered", true)
                    navController.popBackStack()
                },
                onGoToLogin = { navController.popBackStack() }
            )
        }
    }
}