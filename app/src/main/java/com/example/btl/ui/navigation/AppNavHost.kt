package com.example.btl.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.btl.ui.cookathome.Nautainha
import com.example.btl.ui.detail.CongthucScreen
import com.example.btl.ui.favorite.Favorite
import com.example.btl.ui.home.HomeScreen
import com.example.btl.ui.onboarding.Onboarding
import com.example.btl.ui.profile.Profile
import com.example.btl.ui.search.SearchScreen

@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier = Modifier) {

    NavHost(navController = navController, startDestination = Screen.Onboarding.route, modifier = modifier){

        composable(Screen.Onboarding.route) {
            Onboarding(
                onDone = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onRecipeClick = { recipeId ->
                    navController.navigate(Screen.Details.createRoute(recipeId))
                },
                onSearchClick = { navController.navigate(Screen.Search.route) }
            )
        }

        composable(Screen.CookAtHome.route) {
            Nautainha()
        }

        composable(Screen.Profile.route) {
            Profile(
                onFavoritesClick = { navController.navigate(Screen.Favorites.route) }
            )
        }

        composable(Screen.Favorites.route) {
            Favorite()
        }

        composable(route = Screen.Search.route) {
            SearchScreen(
                onResultClick = { recipeId ->
                    navController.navigate(Screen.Details.createRoute(recipeId))
                },
                onNavigateUp = { navController.navigateUp() }
            )
        }

        composable(route = Screen.Details.route) {
            CongthucScreen(
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}