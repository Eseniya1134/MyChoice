package com.mychoice.auth.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onGoToLogin: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    var passwordVisible by remember { mutableStateOf(false) }
    var roleExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            viewModel.resetSuccess()
            onRegisterSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(48.dp))

        Text(
            text = "Регистрация",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Заполните данные для регистрации",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.height(32.dp))

        // Имя + Фамилия
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = state.firstName,
                onValueChange = viewModel::onFirstNameChange,
                label = { Text("Имя") },
                leadingIcon = { Icon(Icons.Outlined.Person, contentDescription = null) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Right) }),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = state.lastName,
                onValueChange = viewModel::onLastNameChange,
                label = { Text("Фамилия") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = state.email,
            onValueChange = viewModel::onEmailChange,
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = null) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = state.password,
            onValueChange = viewModel::onPasswordChange,
            label = { Text("Пароль (минимум 6 символов)") },
            leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Outlined.VisibilityOff
                        else Icons.Outlined.Visibility,
                        contentDescription = null
                    )
                }
            },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        // Возраст + Город
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = state.ageText,
                onValueChange = viewModel::onAgeChange,
                label = { Text("Возраст") },
                leadingIcon = { Icon(Icons.Outlined.DateRange, contentDescription = null) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Right) }),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = state.city,
                onValueChange = viewModel::onCityChange,
                label = { Text("Город") },
                leadingIcon = { Icon(Icons.Outlined.LocationOn, contentDescription = null) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(16.dp))

        // Роль (необязательно)
        // Вместо ExposedDropdownMenuBox используем Box + DropdownMenu

        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = state.role?.displayName ?: "",
                onValueChange = {},
                readOnly = true,
                label = { Text("Роль (необязательно)") },
                leadingIcon = { Icon(Icons.Outlined.Badge, contentDescription = null) },
                trailingIcon = {
                    IconButton(onClick = { roleExpanded = !roleExpanded }) {
                        Icon(
                            imageVector = if (roleExpanded) Icons.Outlined.KeyboardArrowUp
                            else Icons.Outlined.KeyboardArrowDown,
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            DropdownMenu(
                expanded = roleExpanded,
                onDismissRequest = { roleExpanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                DropdownMenuItem(
                    text = { Text("Не указывать") },
                    onClick = { viewModel.onRoleChange(null); roleExpanded = false }
                )
                UserRole.entries.forEach { role ->
                    DropdownMenuItem(
                        text = { Text(role.displayName) },
                        onClick = { viewModel.onRoleChange(role); roleExpanded = false }
                    )
                }
            }
        }

        AnimatedVisibility(visible = state.errorMessage != null) {
            state.errorMessage?.let {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(Modifier.height(28.dp))

        Button(
            onClick = { focusManager.clearFocus(); viewModel.register() },
            enabled = !state.isLoading,
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(22.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.5.dp
                )
            } else {
                Text("Зарегистрироваться", style = MaterialTheme.typography.titleMedium)
            }
        }

        Spacer(Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Уже есть аккаунт?",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            TextButton(onClick = onGoToLogin) {
                Text("Войти", fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(Modifier.height(48.dp))
    }
}