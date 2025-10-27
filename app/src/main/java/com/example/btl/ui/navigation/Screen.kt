package com.example.btl.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import java.lang.Exception
import java.net.URLEncoder

sealed class Screen(val route: String, val label: String? = null, val icon: ImageVector? = null) {
    object Home : Screen("home?q={q}", "Home", Icons.Default.Home) {
        fun createRoute(query: String): String {
            val encodedQuery = try {
                URLEncoder.encode(query, "UTF-8")
            } catch (e: Exception) {
                ""
            }
            return "home?q=$encodedQuery"
        }
    }

    object Profile : Screen("profile", "Cá nhân", Icons.Default.Person)
    object Login : Screen("login")
    object Register : Screen("register")

    object VerifyEmail : Screen("verify_email/{email}") {
        fun createRoute(email: String): String {
            val encodedEmail = try {
                java.net.URLEncoder.encode(email, "UTF-8")
            } catch (e: Exception) {
                email
            }
            return "verify_email/$encodedEmail"
        }
    }

    object Onboarding : Screen("onboarding")

    object Search : Screen("search?q={q}") {
        fun createRoute(query: String): String {
            val encodedQuery = try {
                URLEncoder.encode(query, "UTF-8")
            } catch (e: Exception) {
                ""
            }
            return "search?q=$encodedQuery"
        }
        fun createRouteFromHome(): String = "search?q="
    }

    object Details : Screen("details/{id}") {
        fun createRoute(id: String) = "details/$id"
    }

    companion object {
        val bottomBarItems = listOf(Home, Profile)
    }
}