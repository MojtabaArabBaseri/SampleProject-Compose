package ir.millennium.sampleprojectcompose.presentation.screens.homeScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ir.millennium.sampleprojectcompose.R
import ir.millennium.sampleprojectcompose.presentation.dialog.AboutMeScreen
import ir.millennium.sampleprojectcompose.presentation.theme.AppFont
import ir.millennium.sampleprojectcompose.presentation.theme.Green
import ir.millennium.sampleprojectcompose.presentation.theme.LocalCustomColorsPalette
import ir.millennium.sampleprojectcompose.presentation.theme.NavyColor
import ir.millennium.sampleprojectcompose.presentation.theme.White
import ir.millennium.sampleprojectcompose.presentation.utils.Constants.USER_PROFILE_DATA

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(homeScreenViewModel: HomeScreenViewModel) {

    val modalBottomSheetState = rememberModalBottomSheetState()
    var isExpandedBottomSheet by rememberSaveable { mutableStateOf(false) }

    val systemUiController = rememberSystemUiController()
    val localCustomColorsPalette = LocalCustomColorsPalette.current

    Scaffold {
        LazyColumn(
            state = homeScreenViewModel.stateLazyColumn,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
        ) {

            item {
                ConstraintLayout {
                    val (imageRef, textRef) = createRefs()

                    Image(
                        painter = painterResource(id = R.mipmap.image_user),
                        contentDescription = null,
                        modifier = Modifier
                            .constrainAs(imageRef) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.matchParent
                                height = Dimension.value(300.dp)
                            },
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        text = stringResource(id = R.string.full_name),
                        modifier = Modifier
                            .constrainAs(textRef) {
                                top.linkTo(imageRef.bottom)
                                bottom.linkTo(imageRef.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.wrapContent
                                height = Dimension.wrapContent
                            },
                        style = TextStyle(
                            fontSize = 26.sp,
                            fontFamily = if (true) AppFont.FontPersian else AppFont.FontEnglish,
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic,
                            letterSpacing = 1.sp,
                            color = LocalCustomColorsPalette.current.textColorPrimary,
                            shadow = Shadow(
                                offset = Offset(-3f, -3f),
                                color = LocalCustomColorsPalette.current.supplementTextColorPrimary
                            ),
                        ),
                    )
                }
            }

            items(items = USER_PROFILE_DATA.socialNetwork) { item ->
                rowSocialNetwork(item)
            }

            item {
                Button(
                    onClick = { isExpandedBottomSheet = true },
                    modifier = Modifier
                        .padding(top = 12.dp, bottom = 20.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth()
                        .height(dimensionResource(id = R.dimen.size_height_button)),
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.size_radius_button)),
                    colors = ButtonDefaults.buttonColors(containerColor = Green)
                ) {
                    Text(
                        text = stringResource(id = R.string.aboutMe),
                        color = White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        if (isExpandedBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    systemUiController.setNavigationBarColor(localCustomColorsPalette.navigationBottomColor)
                    isExpandedBottomSheet = false
                },
                sheetState = modalBottomSheetState,
                containerColor = MaterialTheme.colorScheme.background,
                scrimColor = NavyColor.copy(alpha = 0.2f),
                tonalElevation = 0.dp,
            ) {
                AboutMeScreen()
            }
        }
    }
}