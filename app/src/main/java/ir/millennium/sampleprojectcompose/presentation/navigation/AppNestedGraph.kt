package ir.millennium.sampleprojectcompose.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import ir.millennium.sampleprojectcompose.presentation.activity.mainActivity.MainActivityViewModel
import ir.millennium.sampleprojectcompose.presentation.screens.articleScreen.ArticleScreen
import ir.millennium.sampleprojectcompose.presentation.screens.homeScreen.HomeScreen
import ir.millennium.sampleprojectcompose.presentation.screens.mainScreen.MainScreen

@OptIn(ExperimentalMaterialNavigationApi::class)
fun NavGraphBuilder.appGraph(
    navController: NavController,
    mainActivityViewModel: MainActivityViewModel
) {
    navigation(startDestination = Screens.MainScreenRoute.route, route = Screens.AppRoute.route) {

        composable(route = Screens.MainScreenRoute.route) {
            MainScreen(navController = navController, mainActivityViewModel)
        }

        composable(route = Screens.HomeScreenRoute.route) {
            HomeScreen(navController = navController)
        }

        composable(route = Screens.ArticleScreenRoute.route) {
            ArticleScreen(navController = navController)
        }

//        composable(route = "${Screens.ScreenDetailRoute.route}?nameLanguage={nameLanguage}",
//            arguments = listOf(
//                navArgument(name = "nameLanguage") {
//                    type = NavType.StringType
//                    //defaultValue= "user"
//                    nullable = true
//                }
//            )
//        ) { backstackEntry ->
//            ScreenDetail(
//                navController = navController,
//                nameLanguage = backstackEntry.arguments?.getString("nameLanguage")
//            )
//        }
    }
}