package ir.millennium.sampleprojectcompose.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ir.millennium.sampleprojectcompose.presentation.screens.loginScreen.LoginScreen
import ir.millennium.sampleprojectcompose.presentation.screens.loginScreen.LoginScreenViewModel
import ir.millennium.sampleprojectcompose.presentation.screens.splashScreen.SplashScreen
import ir.millennium.sampleprojectcompose.presentation.screens.splashScreen.SplashScreenViewModel

fun NavGraphBuilder.authGraph(navController: NavController) {

    navigation(
        startDestination = Screens.SplashScreenRoute.route,
        route = Screens.AuthRoute.route
    ) {

        composable(route = Screens.SplashScreenRoute.route) {
            val splashScreenViewModel = hiltViewModel<SplashScreenViewModel>(it)
            SplashScreen(navController = navController, splashScreenViewModel)
        }

        composable(route = Screens.LoginScreenRoute.route) {
            val loginScreenViewModel = hiltViewModel<LoginScreenViewModel>(it)
            LoginScreen(navController = navController, loginScreenViewModel)
        }
    }
}