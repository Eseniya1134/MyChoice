package com.mychoice.settings.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.mychoice.resources.R
import android.util.Log


@Composable
fun SettingsScreen(
    onNavigateToProfile: () -> Unit,
    onNavigateToEditProfile: () -> Unit,
    viewModel: SettingsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.selectedLanguage) {
        Log.d("LANG_DEBUG", "UI LANGUAGE NOW: ${uiState.selectedLanguage}")
    }

    if (uiState.showLanguageDialog) {
        LanguagePickerDialog(
            currentLanguage = uiState.selectedLanguage,
            onSelect = { viewModel.onLanguageSelected(it) },
            onDismiss = { viewModel.onLanguageDialogDismiss() }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        ProfileHeader(
            username = uiState.username,
            handle = uiState.handle,
            avatarUrl = uiState.avatarUrl,
            onHeaderClick = onNavigateToProfile,
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
                icon = if (uiState.isLightTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                iconTint = MaterialTheme.colorScheme.primary,
                title = if (uiState.isLightTheme)
                    stringResource(R.string.theme_light)
                else
                    stringResource(R.string.theme_dark),
                checked = uiState.isLightTheme,
                onToggle = { viewModel.toggleTheme(it) }
            )

            SettingsNavigationItem(
                icon = Icons.Default.Language,
                iconTint = MaterialTheme.colorScheme.tertiary,
                title = stringResource(R.string.language),
                subtitle = uiState.selectedLanguage,
                onClick = { viewModel.onLanguageClick() }
            )

            SettingsNavigationItem(
                icon = Icons.Default.Notifications,
                iconTint = MaterialTheme.colorScheme.secondary,
                title = stringResource(R.string.notifications),
                onClick = {}
            )

            SettingsNavigationItem(
                icon = Icons.Default.Lock,
                iconTint = MaterialTheme.colorScheme.primary,
                title = stringResource(R.string.privacy),
                onClick = {}
            )

            SettingsNavigationItem(
                icon = Icons.Default.Info,
                iconTint = MaterialTheme.colorScheme.secondary,
                title = stringResource(R.string.about_app),
                onClick = {}
            )

            SettingsNavigationItem(
                icon = Icons.Default.HelpOutline,
                iconTint = MaterialTheme.colorScheme.tertiary,
                title = stringResource(R.string.faq),
                subtitle = stringResource(R.string.faq_subtitle),
                onClick = {}
            )

            SettingsNavigationItem(
                icon = Icons.AutoMirrored.Filled.Logout,
                iconTint = MaterialTheme.colorScheme.error,
                title = stringResource(R.string.logout),
                tintRed = true,
                onClick = { viewModel.onLogout() }
            )
        }
    }
}

// Диалог выбора языка
@Composable
private fun LanguagePickerDialog(
    currentLanguage: String,
    onSelect: (AppLanguage) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(20.dp),
        title = {
            Text(
                text = stringResource(R.string.choose_language),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                AppLanguage.entries.forEach { lang ->
                    val isSelected = lang.displayName == currentLanguage

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected)
                            MaterialTheme.colorScheme.primaryContainer
                        else Color.Transparent,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelect(lang) }
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = if (lang == AppLanguage.RUSSIAN) "🇷🇺" else "🇬🇧",
                                    fontSize = 22.sp
                                )
                                Spacer(Modifier.width(12.dp))
                                Text(
                                    text = lang.displayName,
                                    fontSize = 15.sp,
                                    fontWeight = if (isSelected)
                                        FontWeight.SemiBold
                                    else FontWeight.Normal
                                )
                            }

                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
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
    val gradientColors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.primaryContainer
    )

    Card(
        shape     = RoundedCornerShape(14.dp),
        modifier  = Modifier
            .fillMaxWidth()
            .clickable { onHeaderClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        border    = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        elevation = CardDefaults.cardElevation(0.dp),
        colors    = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = gradientColors,
                        start  = androidx.compose.ui.geometry.Offset(0f, 0f),
                        end    = androidx.compose.ui.geometry.Offset(
                            Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY
                        )
                    )
                )
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier          = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (avatarUrl != null) {
                            AsyncImage(
                                model              = avatarUrl,
                                contentDescription = stringResource(R.string.avatar_content_description),
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
                    modifier  = Modifier.fillMaxWidth().height(44.dp),
                    shape     = RoundedCornerShape(22.dp),
                    colors    = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.15f),
                        contentColor   = MaterialTheme.colorScheme.onPrimary
                    ),
                    elevation = ButtonDefaults.buttonElevation(0.dp),
                    border    = BorderStroke(
                        1.dp, MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.4f)
                    )
                ) {
                    Text(stringResource(R.string.edit_profile), fontSize = 15.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

// Тоггл с иконкой

@Composable
private fun SettingsToggleItem(
    icon: ImageVector,
    iconTint: Color,
    title: String,
    checked: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Surface(
        shape    = RoundedCornerShape(14.dp),
        color    = MaterialTheme.colorScheme.surfaceContainerLow,
        border   = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier          = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Иконка в цветном кружке
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(iconTint.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector        = icon,
                    contentDescription = null,
                    tint               = iconTint,
                    modifier           = Modifier.size(20.dp)
                )
            }
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

// Пункт со стрелкой и иконкой

@Composable
private fun SettingsNavigationItem(
    icon: ImageVector,
    iconTint: Color,
    title: String,
    subtitle: String? = null,
    tintRed: Boolean = false,
    onClick: () -> Unit
) {
    Surface(
        shape    = RoundedCornerShape(14.dp),
        color    = MaterialTheme.colorScheme.surfaceContainerLow,
        border   = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier          = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Иконка в цветном кружке
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(iconTint.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector        = icon,
                    contentDescription = null,
                    tint               = iconTint,
                    modifier           = Modifier.size(20.dp)
                )
            }
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
                tint               = if (tintRed) MaterialTheme.colorScheme.error
                else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier           = Modifier.size(22.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    SettingsScreen(
        onNavigateToProfile     = {},
        onNavigateToEditProfile = {}
    )
}