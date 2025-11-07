package com.abraham.toolboxchanllenge.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.abraham.toolboxchanllenge.ui.screens.DetailScreen
import com.abraham.toolboxchanllenge.ui.screens.HomeScreen
import com.abraham.toolboxchanllenge.ui.viewmodel.PhotosViewModel

@Composable
fun NavGraph() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = "home") {
        composable("home") {
            val vm: PhotosViewModel = hiltViewModel()
            HomeScreen(vm) { photoId ->
                nav.navigate("detail/$photoId")
            }
        }
        composable(
            "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { back ->
            val id = back.arguments?.getString("id") ?: ""
            val vm: PhotosViewModel = hiltViewModel()
            DetailScreen(photoId = id, vm = vm, onBack = { nav.popBackStack() })
        }
    }
}
