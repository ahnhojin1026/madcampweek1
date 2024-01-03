package com.example.madcampcom1.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun ContactNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = "contact"
    ) {
        composable("contact") {
            ContactScreen(contactViewModel = hiltViewModel(),
                onNavigateToDetail = { navController.navigate("contactDetail") })
        }
        composable("contactDetail") { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry("contact")
            }
            ContactDetailScreen(contactViewModel = hiltViewModel(parentEntry),
                onPop = { navController.popBackStack() })
        }
    }
}