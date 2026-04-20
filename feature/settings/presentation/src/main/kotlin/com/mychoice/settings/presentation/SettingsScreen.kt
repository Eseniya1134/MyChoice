package com.mychoice.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage

@Composable
fun SettingsScreen(
    onNavigateToProfile: () -> Unit,
    onNavigateToEditProfile: () -> Unit,
    viewModel: SettingsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        ProfileHeader(
            username           = uiState.username,
            handle             = uiState.handle,
            avatarUrl          = uiState.avatarUrl,
            onHeaderClick      = onNavigateToProfile,
            onEditProfileClick = onNavigateToEditProfile
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SettingsToggleItem(
                title    = "Светлая тема",
                checked  = uiState.isLightTheme,
                onToggle = { viewModel.toggleTheme(it) }
            )
            SettingsNavigationItem(
                title    = "Язык",
                subtitle = uiState.selectedLanguage,
                onClick  = { viewModel.onLanguageClick() }
            )
            SettingsNavigationItem(title = "Уведомления",       onClick = {})
            SettingsNavigationItem(title = "Конфиденциальность", onClick = {})
            SettingsNavigationItem(title = "О приложении",       onClick = {})
            SettingsNavigationItem(
                title   = "Выйти",
                tintRed = true,
                onClick = { viewModel.onLogout() }
            )
        }
    }
}

// Шапка

@Composable
private fun ProfileHeader(
    username: String,
    handle: String,
    avatarUrl: String?,
    onHeaderClick: () -> Unit,
    onEditProfileClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primaryContainer
                    )
                )
            )
            .clickable { onHeaderClick() }
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Аватар
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(
                            MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (avatarUrl != null) {
                        AsyncImage(
                            model              = avatarUrl,
                            contentDescription = "Аватар",
                            contentScale       = ContentScale.Crop,
                            modifier           = Modifier.fillMaxSize()
                        )
                    } else {
                        Text(
                            text       = username.take(2).uppercase(),
                            color      = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize   = 22.sp
                        )
                    }
                }

                Spacer(Modifier.width(16.dp))

                Column {
                    Text(
                        text     = "@$handle",
                        color    = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.75f),
                        fontSize = 13.sp
                    )
                    Text(
                        text       = username,
                        color      = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize   = 22.sp
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            Button(
                onClick   = { onEditProfileClick() },
                modifier  = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                shape     = RoundedCornerShape(22.dp),
                colors    = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor   = MaterialTheme.colorScheme.onSecondary
                ),
                elevation = ButtonDefaults.buttonElevation(0.dp)
            ) {
                Text(
                    text       = "Редактировать",
                    fontSize   = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

// Тоггл

@Composable
private fun SettingsToggleItem(
    title: String,
    checked: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Surface(
        shape    = RoundedCornerShape(14.dp),
        color    = MaterialTheme.colorScheme.surfaceContainer,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier          = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text     = title,
                modifier = Modifier.weight(1f),
                fontSize = 15.sp,
                color    = MaterialTheme.colorScheme.onSurface
            )
            Switch(
                checked         = checked,
                onCheckedChange = onToggle,
                colors          = SwitchDefaults.colors(
                    checkedThumbColor   = MaterialTheme.colorScheme.onPrimary,
                    checkedTrackColor   = MaterialTheme.colorScheme.primary,
                    uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        }
    }
}

//  Пункт со стрелкой

@Composable
private fun SettingsNavigationItem(
    title: String,
    subtitle: String? = null,
    tintRed: Boolean = false,
    onClick: () -> Unit
) {
    Surface(
        shape    = RoundedCornerShape(14.dp),
        color    = MaterialTheme.colorScheme.surfaceContainer,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier          = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text     = title,
                    fontSize = 15.sp,
                    color    = if (tintRed) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.onSurface
                )
                if (subtitle != null) {
                    Text(
                        text     = subtitle,
                        fontSize = 12.sp,
                        color    = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Icon(
                imageVector        = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint               = MaterialTheme.colorScheme.primary,
                modifier           = Modifier.size(22.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    SettingsScreen(
        onNavigateToProfile = {},
        onNavigateToEditProfile = {}
    )
}