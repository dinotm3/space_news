package dt.mesaric.spacenews.presentation.navigation

import androidx.compose.animation.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import dt.mesaric.spacenews.presentation.news_detail.NewsDetailScreen
import dt.mesaric.spacenews.presentation.news_list.NewsListScreen
import dt.mesaric.spacenews.presentation.screen.NewsScreen

@ExperimentalAnimationApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun MainNav() {
    val navController = rememberNavController()
    Scaffold(
        content = {
            NavHost(
                navController = navController,
                startDestination = NewsScreen.NewsListScreen.route,
            ) {
                composable(
                    route = NewsScreen.NewsListScreen.route
                ) {
                    NewsListScreen(navController)
                }
                composable(
                    route = NewsScreen.NewsDetailScreen.route + "/{articleId}"
                ) {
                    NewsDetailScreen()
                }
            }
        })
}