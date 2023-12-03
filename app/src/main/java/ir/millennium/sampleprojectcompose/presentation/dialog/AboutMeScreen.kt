package ir.millennium.sampleprojectcompose.presentation.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ir.millennium.sampleprojectcompose.R
import ir.millennium.sampleprojectcompose.presentation.theme.LocalCustomColorsPalette

@Preview
@Composable
fun AboutMeScreen() {

    rememberSystemUiController().setNavigationBarColor(MaterialTheme.colorScheme.background)

    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.about_me_detail),
            color = LocalCustomColorsPalette.current.textColorPrimary,
            style = MaterialTheme.typography.bodyMedium,
            lineHeight = 30.sp
        )
    }
}