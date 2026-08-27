// SettingsNavGraph.kt
package com.mychoice.settings.presentation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mychoice.auth.presentation.LoginScreen
import com.mychoice.profile.presentation.ProfileScreen
import com.mychoice.auth.presentation.AuthRoutes

object SettingsDestination {
    const val SETTINGS     = "settings"
    const val PROFILE      = "profile"
    const val EDIT_PROFILE = "edit_profile"
    const val LOG_OUT      = "log_out"
}

fun NavGraphBuilder.settingsGraph(
    navController: NavController,
    onAuthSuccess: () -> Unit,
    onLogoutRequested: () -> Unit,  // 👈 ДОБАВЛЯЕМ
    snackbarHostState: SnackbarHostState
) {
    composable(SettingsDestination.SETTINGS) {
        SettingsScreen(
            onNavigateToProfile = {
                navController.navigate(SettingsDestination.PROFILE)
            },
            onNavigateToEditProfile = {
                navController.navigate(SettingsDestination.EDIT_PROFILE)
            },
            onLogout = {
                onLogoutRequested()  // 👈 ПЕРЕДАЕМ НАВЕРХ
            }
        )
    }

    composable(SettingsDestination.PROFILE) {
        ProfileScreen(
            onNavigateToSettings = {
                navController.popBackStack()
            },
            onNavigateToEdit = {
                navController.navigate(SettingsDestination.EDIT_PROFILE)
            }
        )
    }

    composable(SettingsDestination.LOG_OUT) {
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

//    composable(SettingsDestination.EDIT_PROFILE) {
//        EditProfileScreen(
//            onBack = {
//                navController.popBackStack()
//            }
//        )
//    }
}