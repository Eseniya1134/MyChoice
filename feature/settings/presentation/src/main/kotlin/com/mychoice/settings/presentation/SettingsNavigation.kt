package com.mychoice.settings.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mychoice.profile.presentation.ProfileScreen

object SettingsDestination {
    const val SETTINGS     = "settings"
    const val PROFILE      = "profile"
    const val EDIT_PROFILE = "edit_profile"
}

fun NavGraphBuilder.settingsGraph(navController: NavController) {
    composable(SettingsDestination.SETTINGS) {
        SettingsScreen(
            onNavigateToProfile = {
                navController.navigate(SettingsDestination.PROFILE)
            },
            onNavigateToEditProfile = {
                navController.navigate(SettingsDestination.EDIT_PROFILE)
            }
        )
    }

    composable(SettingsDestination.PROFILE) {
        ProfileScreen(
            onNavigateToSettings = {   // возврат на настройки
                navController.popBackStack()
            },
            onNavigateToEdit = {
                navController.navigate(SettingsDestination.EDIT_PROFILE)
            }
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