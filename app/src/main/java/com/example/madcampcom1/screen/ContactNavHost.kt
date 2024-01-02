package com.example.madcampcom1.screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument

@Composable
fun ContactNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = "contact"
    ) {
        composable("contact") {
            ContactScreen(contactViewModel = hiltViewModel(),
                onNavigateToDetail = { contactId -> navController.navigate("contactDetail/${contactId}") })
        }
        composable(
            "contactDetail/{contactId}",
            arguments = listOf(navArgument("contactId") { type = NavType.IntType })
        ) { backStackEntry ->
            ContactDetailScreen(backStackEntry.arguments!!.getInt("contactId"))
        }
    }
}