package ir.millennium.sampleprojectcompose.presentation.activity.mainActivity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.core.view.WindowCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.millennium.sampleprojectcompose.core.ui.BaseActivity
import ir.millennium.sampleprojectcompose.domain.entity.TypeTheme
import ir.millennium.sampleprojectcompose.presentation.navigation.NavGraph
import ir.millennium.sampleprojectcompose.presentation.navigation.Screens
import ir.millennium.sampleprojectcompose.presentation.theme.AppTheme

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val mainActivityViewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            when (navBackStackEntry?.destination?.route) {
                Screens.SplashScreenRoute.route -> mainActivityViewModel.onAuthScreen(true)
                Screens.LoginScreenRoute.route -> mainActivityViewModel.onAuthScreen(true)
                else -> mainActivityViewModel.onAuthScreen(false)
            }

            AppTheme(
                typeTheme = mainActivityViewModel.typeTheme.value,
                authScreens = mainActivityViewModel.authScreen.value,
                languageApp = mainActivityViewModel.languageApp.value
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph(navController, mainActivityViewModel)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview(@PreviewParameter(ThemeProvider::class) typeTheme: Int) {
//    MyApp(statusTheme = typeTheme) {
//        NavGraph(mainActivityViewModel)
//    }
}

class ThemeProvider : PreviewParameterProvider<Int> {
    override val values = listOf(TypeTheme.LIGHT.typeTheme, TypeTheme.DARK.typeTheme).asSequence()
}
