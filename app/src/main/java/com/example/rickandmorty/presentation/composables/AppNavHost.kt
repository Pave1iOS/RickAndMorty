package com.example.rickandmorty.presentation.composables

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rickandmorty.presentation.screens.detailScreen.DetailsScreenContainer
import com.example.rickandmorty.presentation.screens.mainScreen.MainScreenContainer

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    val gridState = rememberSaveable(saver = LazyGridState.Saver) {
        LazyGridState()
    }

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable("main") {
            MainScreenContainer(
                gridState = gridState,
                onClick = { id -> navController.navigate("details/$id") }
            )
        }

        composable(
            route = "details/{characterId}",
            arguments = listOf(navArgument("characterId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("characterId") ?: return@composable

            DetailsScreenContainer(
                characterId = id,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

