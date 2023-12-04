package ir.millennium.sampleprojectcompose.presentation.screens.splashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ir.millennium.sampleprojectcompose.R
import ir.millennium.sampleprojectcompose.domain.entity.TypeTheme
import ir.millennium.sampleprojectcompose.presentation.navigation.Screens
import ir.millennium.sampleprojectcompose.presentation.utils.Constants
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, splashScreenViewModel: SplashScreenViewModel) {

    val coroutineScope = rememberCoroutineScope()

    Box(modifier = with(Modifier) {
        fillMaxSize()
            .navigationBarsPadding()
            .paint(
                painterResource(id = if (splashScreenViewModel.sharedPreferencesManager.getStatusTheme() == TypeTheme.DARK.typeTheme) R.drawable.background_splash_dark_theme else R.drawable.background_login_light_theme),
                contentScale = ContentScale.FillBounds
            )
    })
    {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.mipmap.logo),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(width = 280.dp, height = 150.dp)
            )
        }
    }

    LaunchedEffect(coroutineScope) {
        delay(Constants.SPLASH_DISPLAY_LENGTH)
        if (splashScreenViewModel.sharedPreferencesManager.getStatusLoginUser()) {
            navController.navigate(Screens.MainScreenRoute.route) {
                popUpTo(Screens.SplashScreenRoute.route) { inclusive = true }
            }
        } else {
            navController.navigate(Screens.LoginScreenRoute.route) {
                popUpTo(Screens.SplashScreenRoute.route) { inclusive = true }
            }
        }
    }
}