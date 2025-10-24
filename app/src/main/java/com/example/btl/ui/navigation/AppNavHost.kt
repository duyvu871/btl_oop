package com.example.btl.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.btl.ui.auth.LoginScreen
import com.example.btl.ui.auth.RegisterScreen
import com.example.btl.ui.auth.VerifyEmailScreen
import com.example.btl.ui.cookathome.Nautainha
import com.example.btl.ui.detail.CongthucScreen
import com.example.btl.ui.favorite.Favorite
import com.example.btl.ui.home.HomeScreen
import com.example.btl.ui.onboarding.Onboarding
import com.example.btl.ui.profile.Profile
import com.example.btl.ui.search.SearchScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = modifier
    ) {

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }


        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = { email ->
                    navController.navigate(Screen.VerifyEmail.createRoute(email)) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }


        composable(
            route = Screen.VerifyEmail.route,
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) {
            VerifyEmailScreen(
                onVerifySuccess = {

                    navController.navigate(Screen.Login.route) {

                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = true
                        }
                    }
                }
            )
        }




        composable(Screen.Onboarding.route) {
            Onboarding(
                onDone = { query ->
                    navController.navigate(Screen.Home.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                    navController.navigate(Screen.Search.createRoute(query))
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onRecipeClick = { recipeId ->
                    navController.navigate(Screen.Details.createRoute(recipeId))
                },
                onSearchClick = {
                    navController.navigate(Screen.Search.createRouteFromHome())
                }
            )
        }

        composable(
            route = Screen.Search.route,
            arguments = listOf(navArgument("q") {
                type = NavType.StringType
                defaultValue = ""
            })
        ) {
            SearchScreen(
                onResultClick = { recipeId ->
                    navController.navigate(Screen.Details.createRoute(recipeId))
                },
                onNavigateUp = {
                    navController.navigateUp()
                }
            )
        }

        composable(Screen.Details.route) {
            CongthucScreen(
                onNavigateUp = {
                    navController.navigateUp()
                }
            )
        }
        composable(Screen.CookAtHome.route) { Nautainha() }
        composable(Screen.Profile.route) {
            Profile(
                onFavoritesClick = {
                    navController.navigate(Screen.Favorites.route)
                },
                onLogoutClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(Screen.Favorites.route) { Favorite() }
    }
}