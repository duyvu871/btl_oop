package com.example.btl.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String? = null, val icon: ImageVector? = null) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object CookAtHome : Screen("cook_at_home", "Nấu tại nhà", Icons.Default.Kitchen)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)
    object Login : Screen("login")
    object Register : Screen("register")
    object Onboarding : Screen("onboarding")
    // Các màn hình không có trên bottom bar
    object Search : Screen("search")
    object Favorites : Screen("favorites")
    object Details : Screen("details/{id}") {
        fun createRoute(id: String) = "details/$id"
    }

    companion object {
        val bottomBarItems = listOf(Home, CookAtHome, Profile)
    }
}