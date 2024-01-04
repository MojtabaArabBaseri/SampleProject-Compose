package ir.millennium.sampleprojectcompose.presentation.screens.detailArticleScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ir.millennium.sampleprojectcompose.R
import ir.millennium.sampleprojectcompose.data.model.remote.ArticleItem
import ir.millennium.sampleprojectcompose.presentation.theme.GrayDark
import ir.millennium.sampleprojectcompose.presentation.theme.LocalCustomColorsPalette

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailArticleScreen(navController: NavController, articleItem: ArticleItem) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(MaterialTheme.colorScheme.background)
    ) {

        TopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            windowInsets = WindowInsets(
                top = 0, bottom = 0
            ),
            title = {
                Text(
                    text = stringResource(id = R.string.detail_article),
                    color = LocalCustomColorsPalette.current.textColorPrimary,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_back_toolbar),
                        contentDescription = "Back Icon",
                        tint = LocalCustomColorsPalette.current.iconColorPrimary
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = LocalCustomColorsPalette.current.toolbarColor
            )
        )

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {

                val (imageRef, titleRef, authorRef, dateRef, descriptionRef) = createRefs()

                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(articleItem.urlToImage)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .constrainAs(imageRef) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.matchParent
                        }
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.size_radius_editText)))
                        .border(
                            0.dp,
                            Color.Transparent,
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.size_radius_editText))
                        ),
                    contentScale = ContentScale.Crop
                )

                articleItem.title?.let {
                    Text(
                        text = it,
                        modifier = Modifier
                            .constrainAs(titleRef) {
                                top.linkTo(imageRef.bottom)
                                start.linkTo(parent.start)
                            }
                            .padding(start = 16.dp, end = 16.dp, top = 12.dp),
                        fontWeight = FontWeight.Bold,
                        color = LocalCustomColorsPalette.current.textColorPrimary,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                articleItem.author?.let {
                    Text(
                        text = it,
                        modifier = Modifier
                            .constrainAs(authorRef) {
                                top.linkTo(titleRef.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(dateRef.start)
                                width = Dimension.fillToConstraints
                            }
                            .padding(top = 12.dp, start = 16.dp, end = 16.dp),
                        color = GrayDark,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                articleItem.publishedAt?.let {
                    Text(
                        text = it,
                        modifier = Modifier
                            .constrainAs(dateRef) {
                                top.linkTo(titleRef.bottom)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            }
                            .padding(top = 12.dp, end = 16.dp),
                        color = GrayDark,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                articleItem.content?.let {
                    Text(
                        text = it,
                        modifier = Modifier
                            .constrainAs(descriptionRef) {
                                top.linkTo(authorRef.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                        fontWeight = FontWeight.Bold,
                        color = LocalCustomColorsPalette.current.textColorPrimary,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}