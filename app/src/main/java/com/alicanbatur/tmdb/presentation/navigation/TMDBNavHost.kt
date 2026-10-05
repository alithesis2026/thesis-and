package com.alicanbatur.tmdb.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.alicanbatur.tmdb.presentation.favorites.FavoritesScreen
import com.alicanbatur.tmdb.presentation.moviedetail.MovieDetailScreen
import com.alicanbatur.tmdb.presentation.movielist.MovieListScreen
import com.alicanbatur.tmdb.presentation.search.SearchScreen
import com.alicanbatur.tmdb.presentation.settings.SettingsScreen

private data class BottomTab(val destination: Destination, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)

private val bottomTabs = listOf(
    BottomTab(Destination.MovieList, "Movies", Icons.Default.Home),
    BottomTab(Destination.Search, "Search", Icons.Default.Search),
    BottomTab(Destination.Favorites, "Favorites", Icons.Default.Favorite),
    BottomTab(Destination.Settings, "Settings", Icons.Default.Settings)
)

@Composable
fun TMDBNavHost() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination

    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomTabs.forEach { tab ->
                    NavigationBarItem(
                        selected = currentRoute?.hierarchy?.any { it.route == tab.destination.route } == true,
                        onClick = {
                            navController.navigate(tab.destination.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(tab.icon, contentDescription = tab.label) },
                        label = { Text(tab.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Destination.MovieList.route,
            modifier = androidx.compose.ui.Modifier.padding(innerPadding)
        ) {
            composable(Destination.MovieList.route) {
                MovieListScreen(onMovieClick = { movieId ->
                    navController.navigate(Destination.MovieDetail.createRoute(movieId))
                })
            }
            composable(Destination.Search.route) {
                SearchScreen(onMovieClick = { movieId ->
                    navController.navigate(Destination.MovieDetail.createRoute(movieId))
                })
            }
            composable(Destination.Favorites.route) {
                FavoritesScreen(onMovieClick = { movieId ->
                    navController.navigate(Destination.MovieDetail.createRoute(movieId))
                })
            }
            composable(Destination.Settings.route) {
                SettingsScreen()
            }
            composable(
                route = Destination.MovieDetail.route,
                arguments = listOf(navArgument(Destination.MovieDetail.ARG_MOVIE_ID) { type = NavType.IntType })
            ) {
                MovieDetailScreen(onBack = { navController.popBackStack() })
            }
        }
    }
}
