package ir.millennium.sampleprojectcompose.presentation.screens.homeScreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ir.millennium.sampleprojectcompose.R
import ir.millennium.sampleprojectcompose.data.model.local.aboutMe.UserProfileSocialNetworkEntity
import ir.millennium.sampleprojectcompose.presentation.dialog.AboutMeScreen
import ir.millennium.sampleprojectcompose.presentation.theme.AppFont
import ir.millennium.sampleprojectcompose.presentation.theme.LocalCustomColorsPalette
import ir.millennium.sampleprojectcompose.presentation.theme.NavyColor
import ir.millennium.sampleprojectcompose.presentation.theme.White
import ir.millennium.sampleprojectcompose.presentation.utils.Constants.USER_PROFILE_DATA

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    val systemUiController = rememberSystemUiController()
    val localCustomColorsPalette = LocalCustomColorsPalette.current

    Scaffold {
        LazyColumn(
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
                    onClick = { showBottomSheet = true },
                    modifier = Modifier
                        .padding(top = 12.dp, bottom = 20.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth()
                        .height(dimensionResource(id = R.dimen.size_height_button)),
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.size_radius_button)),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
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

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    systemUiController.setNavigationBarColor(localCustomColorsPalette.navigationBottomColor)
                    showBottomSheet = false
                },
                sheetState = sheetState,
                containerColor = MaterialTheme.colorScheme.background,
                scrimColor = NavyColor.copy(alpha = 0.2f),
                tonalElevation = 0.dp,
            ) {
                AboutMeScreen()
            }
        }
    }
}

@Composable
fun rowSocialNetwork(item: UserProfileSocialNetworkEntity) {
    val context = LocalContext.current
//    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.max_height_editText))
            .padding(top = 4.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.size_radius_editText)))
            .clickable { navToSocialNetwork(context, item) },
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.size_radius_editText)),
        colors = CardDefaults.cardColors(containerColor = LocalCustomColorsPalette.current.rowSocialNetworkBackground)
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (lblNameSocialNetworkRef, ibArrowRef) = createRefs()
            Text(
                modifier = Modifier
                    .constrainAs(lblNameSocialNetworkRef) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(start = 16.dp),
                text = stringResource(id = item.title),
                color = LocalCustomColorsPalette.current.textColorPrimary,
                style = MaterialTheme.typography.bodyMedium
            )
            IconButton(
                onClick = { navToSocialNetwork(context, item) },
                modifier = Modifier
                    .constrainAs(ibArrowRef) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
                    .padding(end = 8.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_left_arrow),
                    contentDescription = "",
                    tint = LocalCustomColorsPalette.current.textColorPrimary,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
            }
        }
    }
//    }
}

fun navToSocialNetwork(context: Context, item: UserProfileSocialNetworkEntity) {
    startActivity(
        context, Intent(Intent.ACTION_VIEW, Uri.parse(context.resources.getString(item.link))),
        null
    )
}


