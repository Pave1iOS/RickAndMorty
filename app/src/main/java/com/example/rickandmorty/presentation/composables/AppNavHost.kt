package com.example.rickandmorty.presentation.composables

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.rickandmorty.presentation.screens.detailScreen.DetailsScreen
import com.example.rickandmorty.presentation.screens.detailScreen.DetailsViewModel
import com.example.rickandmorty.presentation.screens.mainScreen.MainScreenContainer
import com.example.rickandmorty.presentation.screens.mainScreen.MainViewModel

@Composable
fun AppNavHost(factory: ViewModelProvider.Factory) {
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
            val detailsViewModel: DetailsViewModel = viewModel(factory = factory)

            DetailsScreen(
                state = detailsViewModel.state.collectAsState().value,
                onBack = { navController.popBackStack() }
            )

            LaunchedEffect(id) {
                detailsViewModel.loadCharacter(id)
            }
        }
    }
}

