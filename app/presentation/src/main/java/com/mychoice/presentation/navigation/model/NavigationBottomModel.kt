package com.mychoice.presentation.navigation.model

sealed class NavigationBottomModel(
    val route: String,
    val text: String,
) {
    data object Search : NavigationBottomModel("search", "Вузы")

    data object News : NavigationBottomModel("news", "Новости")

    data object Discussions : NavigationBottomModel("discussions", "Обсуждения")

    data object Settings : NavigationBottomModel("settings", "Настройки")

    companion object {
        val navItems = listOf( Search, News, Discussions, Settings)
    }
}