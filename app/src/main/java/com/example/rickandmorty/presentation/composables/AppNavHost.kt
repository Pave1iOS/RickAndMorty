package com.example.rickandmorty.presentation.composables

import androidx.compose.runtime.Composable
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

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable("main") {
            MainScreenContainer(
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

