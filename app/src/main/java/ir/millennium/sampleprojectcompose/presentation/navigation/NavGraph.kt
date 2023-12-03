package ir.millennium.sampleprojectcompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ir.millennium.sampleprojectcompose.presentation.activity.mainActivity.MainActivityViewModel

@Composable
fun NavGraph(navController: NavHostController, mainActivityViewModel: MainActivityViewModel) {

    NavHost(navController = navController, startDestination = Screens.AuthRoute.route) {

        authGraph(navController)

        appGraph(navController, mainActivityViewModel)
    }
}