package ir.millennium.sampleprojectcompose.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.google.gson.Gson
import ir.millennium.sampleprojectcompose.data.model.remote.ArticleItem
import ir.millennium.sampleprojectcompose.presentation.activity.mainActivity.MainActivityViewModel
import ir.millennium.sampleprojectcompose.presentation.screens.articleScreen.ArticleScreenViewModel
import ir.millennium.sampleprojectcompose.presentation.screens.detailArticleScreen.DetailArticleScreen
import ir.millennium.sampleprojectcompose.presentation.screens.mainScreen.MainScreen

fun NavGraphBuilder.appGraph(
    navController: NavController,
    mainActivityViewModel: MainActivityViewModel
) {
    navigation(startDestination = Screens.MainScreenRoute.route, route = Screens.AppRoute.route) {

        composable(route = Screens.MainScreenRoute.route) {
            val articleScreenViewModel = hiltViewModel<ArticleScreenViewModel>(it)
            MainScreen(
                navController = navController,
                mainActivityViewModel = mainActivityViewModel,
                articleScreenViewModel = articleScreenViewModel
            )
        }

        composable(route = "${Screens.DetailArticleScreenRoute.route}?articleItem={articleItem}",
            arguments = listOf(
                navArgument(name = "articleItem") {
                    type = NavType.StringType
                }
            )
        ) { backstackEntry ->
            val articleItemJson = backstackEntry.arguments?.getString("articleItem")
            val articleItem = Gson().fromJson(articleItemJson, ArticleItem::class.java)
            DetailArticleScreen(
                navController = navController,
                articleItem = articleItem
            )
        }
    }
}