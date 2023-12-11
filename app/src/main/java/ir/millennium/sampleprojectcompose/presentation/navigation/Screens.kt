package ir.millennium.sampleprojectcompose.presentation.navigation

sealed class Screens(val route: String) {

    object SplashScreenRoute : Screens(route = "SplashScreen")
    object LoginScreenRoute : Screens(route = "LoginScreen")
    object MainScreenRoute : Screens(route = "MainScreen")
    object HomeScreenRoute : Screens(route = "HomeScreen")
    object ArticleScreenRoute : Screens(route = "ArticleScreen")
    object DetailArticleScreenRoute : Screens(route = "DetailArticleScreen")
    object AuthRoute : Screens(route = "Auth")

    object AppRoute : Screens(route = "App")
}
