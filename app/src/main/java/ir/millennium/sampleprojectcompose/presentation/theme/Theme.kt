package ir.millennium.sampleprojectcompose.presentation.theme

import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ir.millennium.sampleprojectcompose.domain.entity.TypeLanguage
import ir.millennium.sampleprojectcompose.domain.entity.TypeTheme

private val DarkColorPalette = darkColorScheme(
    primary = Blue,
    onPrimary = White,
    surface = GrayVeryDark,
    onSurface = White,
    background = Black,
    onBackground = White,
    primaryContainer = Black,
    secondary = Green,
)

private val LightColorPalette = lightColorScheme(
    primary = Blue,
    onPrimary = White,
    surface = White,
    onSurface = Black,
    background = White,
    onBackground = Black,
    primaryContainer = White,
    secondary = Red,
    surfaceContainer = White
)

@Composable
fun AppTheme(
    typeTheme: Int = TypeTheme.LIGHT.typeTheme,
    languageApp: String = TypeLanguage.ENGLISH.typeLanguage,
    authScreens: Boolean = true,
    content: @Composable () -> Unit
) {
    val colors = if (typeTheme == TypeTheme.DARK.typeTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val typography = if (languageApp == TypeLanguage.ENGLISH.typeLanguage) {
        TypographyEnglish
    } else {
        TypographyPersian
    }

    val customColorsPalette =
        if (typeTheme == TypeTheme.DARK.typeTheme) {
            DarkCustomColorsPalette
        } else {
            LightCustomColorsPalette
        }

    if (authScreens && (typeTheme == TypeTheme.DARK.typeTheme)) {
        rememberSystemUiController().setSystemBarsColor(color = Color(0xAA000000))
        rememberSystemUiController().setNavigationBarColor(color = Black)
    } else if (!authScreens && typeTheme == TypeTheme.DARK.typeTheme) {
        rememberSystemUiController().setSystemBarsColor(color = Black)
        rememberSystemUiController().setNavigationBarColor(color = Black)
    } else {
        rememberSystemUiController().setSystemBarsColor(StatusbarLightColor)
        rememberSystemUiController().setNavigationBarColor(color = NavigationBottomLightColor)
    }

    CompositionLocalProvider(
        LocalCustomColorsPalette provides customColorsPalette
    ) {
        MaterialTheme(
            colorScheme = colors,
            typography = typography,
            shapes = Shapes()
        ) {
            CompositionLocalProvider(
                LocalRippleTheme provides CustomRippleTheme,
                content = content
            )
        }
    }
}
