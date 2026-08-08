package com.mychoice.search.presentation

import androidx.navigation.*
import androidx.navigation.compose.*
import com.mychoice.search.presentation.search.SearchScreen
import com.mychoice.search.presentation.university.UniversityScreen
// Маршруты
object SearchRoutes {
    const val SEARCH = "search"
    const val UNIVERSITY = "university/{universityId}"
    const val FACULTY = "faculty/{facultyId}"
    const val PROGRAM = "program/{programId}"

    fun university(id: Long) = "university/$id"
    fun faculty(id: Long) = "faculty/$id"
    fun program(id: Long) = "program/$id"
}

fun NavGraphBuilder.searchGraph(navController: NavController) {
    navigation(startDestination = SearchRoutes.SEARCH, route = "search_graph") {

        composable(SearchRoutes.SEARCH) {
            SearchScreen(
                onUniversityClick = { id ->
                    navController.navigate(SearchRoutes.university(id))
                }
            )
        }

        composable(
            route = SearchRoutes.UNIVERSITY,
            arguments = listOf(navArgument("universityId") { type = NavType.LongType })
        ) {
            UniversityScreen(
                onBack = { navController.popBackStack() },
                onFacultyClick = { id -> navController.navigate(SearchRoutes.faculty(id)) }
            )
        }

        composable(
            route = SearchRoutes.FACULTY,
            arguments = listOf(navArgument("facultyId") { type = NavType.LongType })
        ) {
            // FacultyScreen — аналогично UniversityScreen
            // onProgramClick = { id -> navController.navigate(SearchRoutes.program(id)) }
        }

        composable(
            route = SearchRoutes.PROGRAM,
            arguments = listOf(navArgument("programId") { type = NavType.LongType })
        ) {
            // ProgramScreen
        }
    }
}