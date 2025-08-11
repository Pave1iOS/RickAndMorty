package com.example.rickandmorty.presentation.composables

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.rickandmorty.App
import com.example.rickandmorty.presentation.screens.detailScreen.CharacterDetailsScreen
import com.example.rickandmorty.presentation.screens.detailScreen.CharacterDetailsViewModel
import com.example.rickandmorty.presentation.screens.mainScreen.MainScreenContainer
import com.example.rickandmorty.presentation.screens.mainScreen.MainScreenViewModel

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val factory = (LocalContext.current.applicationContext as App)
        .appComponent
        .viewModelFactory()

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable("main") {
            val mainViewModel: MainScreenViewModel = viewModel(factory = factory)
            MainScreenContainer(
                viewModel = mainViewModel,
                onClick = { id -> navController.navigate("details/$id") }
            )
        }

        composable(
            route = "details/{characterId}",
            arguments = listOf(navArgument("characterId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("characterId") ?: return@composable
            val detailsViewModel: CharacterDetailsViewModel = viewModel(factory = factory)
            val state by detailsViewModel.state.collectAsState()

            LaunchedEffect(id) {
                detailsViewModel.loadCharacter(id)
            }

            CharacterDetailsScreen(
                state = state,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
