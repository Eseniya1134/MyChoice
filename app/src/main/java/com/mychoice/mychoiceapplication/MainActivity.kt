package com.mychoice.mychoiceapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.core.os.LocaleListCompat
import com.mychoice.mychoiceapplication.ui.theme.MyChoiceApplicationTheme
import com.mychoice.presentation.navigation.screen.NavigationScreen
import com.mychoice.settings.presentation.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val settingsViewModel: SettingsViewModel = viewModel()
            val uiState by settingsViewModel.uiState.collectAsState()

            LaunchedEffect(Unit) {
                settingsViewModel.restartApp.collect {
                    recreate()
                }
            }

            MyChoiceApplicationTheme(
                darkTheme = !uiState.isLightTheme
            ) {
                NavigationScreen()
            }
        }
    }
}