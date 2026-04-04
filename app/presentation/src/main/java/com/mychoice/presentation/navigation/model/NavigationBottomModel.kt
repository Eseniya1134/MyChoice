package com.mychoice.presentation.navigation.model

sealed class NavigationBottomModel(
    val route: String,
    val text: String,
) {
    data object Search : NavigationBottomModel("search", "Поиск")

    data object News : NavigationBottomModel("news", "Лента")
    data object Rating : NavigationBottomModel("rating", "Рейтинг")

    data object Discussions : NavigationBottomModel("discussions", "Обсуждения")

    data object Settings : NavigationBottomModel("settings", "Настройки")

    companion object {
        fun getNavItems() = listOf( News, Search, Rating, Discussions, Settings)
    }
}