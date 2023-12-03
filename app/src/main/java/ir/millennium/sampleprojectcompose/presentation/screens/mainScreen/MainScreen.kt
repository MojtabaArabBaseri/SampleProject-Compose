package ir.millennium.sampleprojectcompose.presentation.screens.mainScreen

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ir.millennium.sampleprojectcompose.R
import ir.millennium.sampleprojectcompose.domain.entity.NavItemState
import ir.millennium.sampleprojectcompose.domain.entity.TypeLanguage
import ir.millennium.sampleprojectcompose.domain.entity.TypeTheme
import ir.millennium.sampleprojectcompose.presentation.activity.mainActivity.MainActivityViewModel
import ir.millennium.sampleprojectcompose.presentation.dialog.questionDialog
import ir.millennium.sampleprojectcompose.presentation.screens.articleScreen.ArticleScreen
import ir.millennium.sampleprojectcompose.presentation.screens.homeScreen.HomeScreen
import ir.millennium.sampleprojectcompose.presentation.theme.LocalCustomColorsPalette
import ir.millennium.sampleprojectcompose.presentation.theme.Red

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, mainActivityViewModel: MainActivityViewModel) {

    val context = LocalContext.current

    val statusChangeLanguageDialog = remember { mutableStateOf(false) }
    val callbackOnYesButtonQuestionDialog = remember { mutableStateOf(false) }

    val items = listOf(
        NavItemState(
            stringResource(id = R.string.profile),
            (ImageVector.vectorResource(id = R.drawable.ic_user_profile)),
            (ImageVector.vectorResource(id = R.drawable.ic_user_profile))
        ),
        NavItemState(
            stringResource(id = R.string.news),
            (ImageVector.vectorResource(id = R.drawable.ic_news)),
            (ImageVector.vectorResource(id = R.drawable.ic_news))
        )
    )

    var bottomNavState by remember {
        mutableIntStateOf(0)
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)),
                tonalElevation = 0.dp,
                containerColor = LocalCustomColorsPalette.current.navigationBottomColor
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = bottomNavState == index,
                        onClick = { bottomNavState = index },
                        icon = {
                            Icon(
                                imageVector = if (bottomNavState == index) item.selectedIcon else item.unSelectedIcon,
                                contentDescription = item.title
                            )
                        },
                        label = {
                            Text(
                                text = item.title,
                                fontWeight = FontWeight.Normal,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Red,
                            unselectedIconColor = MaterialTheme.colorScheme.onBackground,
                            selectedTextColor = Red,
                            unselectedTextColor = MaterialTheme.colorScheme.onBackground
                        )
                    )
                }
            }
        },
    ) { contentPadding ->
        Box(
            Modifier
                .background(Color.White)
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            if (bottomNavState == 0) {
                HomeScreen(navController = navController)
            } else {
                ArticleScreen(navController = navController)
            }

            CenterAlignedTopAppBar(
                windowInsets = WindowInsets(top = 0, bottom = 0),
                title = {
                    Text(
                        text = stringResource(id = R.string.title_application),
                        color = LocalCustomColorsPalette.current.textColorPrimary,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { statusChangeLanguageDialog.value = true }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_language),
                            contentDescription = "Change Language Icon",
                            tint = LocalCustomColorsPalette.current.iconColorPrimary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (mainActivityViewModel.sharedPreferencesManager.getStatusTheme() == TypeTheme.DARK.typeTheme) {
                            mainActivityViewModel.onThemeChanged(TypeTheme.LIGHT.typeTheme)
                        } else {
                            mainActivityViewModel.onThemeChanged(TypeTheme.DARK.typeTheme)
                        }
                    }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_change_theme),
                            contentDescription = "Change Theme Icon",
                            tint = LocalCustomColorsPalette.current.iconColorPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = LocalCustomColorsPalette.current.toolbarColor
                )
            )
            if (statusChangeLanguageDialog.value) {
                questionDialog(
                    message = stringResource(id = R.string.message_change_language),
                    statusDialog = statusChangeLanguageDialog,
                    callbackOnYesButtonQuestionDialog = callbackOnYesButtonQuestionDialog
                )
            }

            if (callbackOnYesButtonQuestionDialog.value) {
                changeLanguage(mainActivityViewModel, context)
            }
        }
    }
}

fun changeLanguage(mainActivityViewModel: MainActivityViewModel, context: Context) {
    if (mainActivityViewModel.sharedPreferencesManager.getLanguageApp() == TypeLanguage.ENGLISH.typeLanguage) {
        mainActivityViewModel.sharedPreferencesManager.setLanguageApp(
            TypeLanguage.PERSIAN.typeLanguage
        )
    } else {
        mainActivityViewModel.sharedPreferencesManager.setLanguageApp(
            TypeLanguage.ENGLISH.typeLanguage
        )
    }
    (context as? Activity)?.recreate()
}