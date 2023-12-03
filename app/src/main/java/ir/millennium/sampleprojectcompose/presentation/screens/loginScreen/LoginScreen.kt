package ir.millennium.sampleprojectcompose.presentation.screens.loginScreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ir.millennium.sampleprojectcompose.R
import ir.millennium.sampleprojectcompose.data.dataSource.local.sharedPreferences.SharedPreferencesManager
import ir.millennium.sampleprojectcompose.domain.entity.TypeTheme
import ir.millennium.sampleprojectcompose.presentation.navigation.Screens
import ir.millennium.sampleprojectcompose.presentation.theme.GrayDark
import ir.millennium.sampleprojectcompose.presentation.theme.GrayLight
import ir.millennium.sampleprojectcompose.presentation.theme.GrayMedium
import ir.millennium.sampleprojectcompose.presentation.theme.Green
import ir.millennium.sampleprojectcompose.presentation.theme.LocalCustomColorsPalette
import ir.millennium.sampleprojectcompose.presentation.theme.NavyColor
import ir.millennium.sampleprojectcompose.presentation.utils.Constants
import ir.millennium.sampleprojectcompose.presentation.utils.Constants.PASSWORD
import ir.millennium.sampleprojectcompose.presentation.utils.Constants.USER_NAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController, loginScreenViewModel: LoginScreenViewModel) {
    LoginUi(navController, loginScreenViewModel)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginUi(navController: NavController, loginScreenViewModel: LoginScreenViewModel) {

    val focusManager = LocalFocusManager.current

    val context = LocalContext.current

    val focusRequester = remember { FocusRequester() }

    var userNameValue by remember {
        mutableStateOf("")
    }

    var passwordValue by remember {
        mutableStateOf("")
    }

    var visibilityLabelLogin by remember {
        mutableStateOf(true)
    }

    var visibilityProgressBar by remember {
        mutableStateOf(false)
    }

    var statusEnabledCardLogin by remember {
        mutableStateOf(true)
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    val coroutineScope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }

    var isCorrectUserName by rememberSaveable { mutableStateOf(false) }
    var isCorrectPassword by rememberSaveable { mutableStateOf(false) }

    fun validateUserName(inputText: String) {
        isCorrectUserName = inputText.lowercase() != USER_NAME
    }

    fun validatePassword(inputText: String) {
        isCorrectPassword = inputText != PASSWORD
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .paint(
                painterResource(id = if (loginScreenViewModel.sharedPreferencesManager.getStatusTheme() == TypeTheme.DARK.typeTheme) R.drawable.background_splash_dark_theme else R.drawable.background_login_light_theme),
                contentScale = ContentScale.FillBounds
            )
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_logo_from_top)))

            Text(
                text = stringResource(id = R.string.title_application),
                modifier = Modifier.wrapContentSize(),
                color = Green,
                fontFamily = FontFamily(Font(R.font.dana_en_num_bold)),
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 30.sp,
                letterSpacing = 2.sp
            )

            Text(
                text = stringResource(id = R.string.description_application),
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 28.dp, end = 28.dp),
                color = Green,
                fontFamily = FontFamily(Font(R.font.dana_en_num_regular)),
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_box_username_from_logo)))

            OutlinedTextField(
                value = userNameValue,
                onValueChange = { newText ->
                    userNameValue = newText
                    validateUserName(userNameValue)

                },
                isError = isCorrectUserName,

                supportingText = {
                    if (isCorrectUserName) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(id = R.string.username_incorrect),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .background(Transparent)
                    .padding(start = 65.dp, end = 65.dp)
                    .focusRequester(focusRequester),
                label = {
                    Text(
                        text = stringResource(id = R.string.user_name),
                        fontSize = 14.sp,
                        modifier = Modifier.background(Transparent)
                    )
                },
                placeholder = {
                    Text(
                        text = USER_NAME,
                        fontSize = 14.sp,
                        color = GrayDark,
                        modifier = Modifier.background(Transparent)
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = (ImageVector.vectorResource(id = R.drawable.ic_mobile)),
                        contentDescription = "Email Icon",
                        tint = LocalCustomColorsPalette.current.textColorPrimary
                    )
                },
//                trailingIcon = {
//                    IconButton(onClick = {
//                        userNameValue = ""
//                    }) {
//                        Icon(
//                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_eraser),
//                            contentDescription = "Clear Icon",
//                            tint = colorResource(id = R.color.colorSecondaryText)
//                        )
//                    }
//                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),

                keyboardActions = KeyboardActions {
                    validateUserName(userNameValue)
                    focusManager.moveFocus(
                        focusDirection = FocusDirection.Next,
                    )
                },

                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = LocalCustomColorsPalette.current.textColorPrimary,
                    unfocusedTextColor = GrayDark,
                    focusedPlaceholderColor = LocalCustomColorsPalette.current.textColorPrimary,
                    unfocusedPlaceholderColor = GrayDark,
                    focusedContainerColor = GrayLight,
                    unfocusedContainerColor = GrayLight,
                    focusedLabelColor = Green,
                    unfocusedLabelColor = GrayDark,
                    unfocusedBorderColor = GrayDark,
                    focusedBorderColor = NavyColor,
                    cursorColor = Green
                ),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.size_radius_editText))
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = passwordValue, onValueChange = { newText ->
                    passwordValue = newText
                    validatePassword(passwordValue)
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .background(Transparent)
                    .padding(start = 65.dp, end = 65.dp),

                label = {
                    Text(
                        text = stringResource(id = R.string.please_enter_password),
                        fontSize = 14.sp,
                        modifier = Modifier.background(Transparent)
                    )
                },
                isError = isCorrectPassword,
                supportingText = {
                    if (isCorrectPassword) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(id = R.string.password_incorrect),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                placeholder = {
                    Text(
                        text = PASSWORD,
                        fontSize = 14.sp,
                        color = GrayMedium
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = (ImageVector.vectorResource(id = R.drawable.ic_code)),
                        contentDescription = "Code Icon",
                        tint = LocalCustomColorsPalette.current.textColorPrimary
                    )
                },
//                trailingIcon = {
//                    IconButton(onClick = {
//                        userNameValue = ""
//                    }) {
//                        Icon(
//                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_eraser),
//                            contentDescription = "Clear Icon",
//                            tint = colorResource(id = R.color.colorSecondaryText)
//                        )
//                    }
//                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = LocalCustomColorsPalette.current.textColorPrimary,
                    unfocusedTextColor = GrayDark,
                    focusedPlaceholderColor = LocalCustomColorsPalette.current.textColorPrimary,
                    unfocusedPlaceholderColor = GrayDark,
                    focusedContainerColor = GrayLight,
                    unfocusedContainerColor = GrayLight,
                    focusedLabelColor = Green,
                    unfocusedLabelColor = GrayDark,
                    unfocusedBorderColor = GrayDark,
                    focusedBorderColor = NavyColor,
                    cursorColor = Green
                ),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.size_radius_editText))
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.space_btn_regiter_from_lbl_family_sign_up)))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.size_height_button))
                    .padding(start = 65.dp, end = 65.dp)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(28.dp))
                    .clickable(enabled = statusEnabledCardLogin) {
                        if (checkFieldForValidation(
                                userNameValue, passwordValue,
                                snackbarHostState,
                                coroutineScope,
                                context
                            )
                        ) {
                            if (checkAuthentication(
                                    userNameValue.lowercase(),
                                    passwordValue,
                                    snackbarHostState,
                                    coroutineScope,
                                    context
                                )
                            ) {
                                keyboardController?.hide()
                                focusManager.clearFocus()
                                statusEnabledCardLogin = false
                                visibilityLabelLogin = !visibilityLabelLogin
                                visibilityProgressBar = !visibilityProgressBar
                                coroutineScope.launch {
                                    delay(Constants.SPLASH_DISPLAY_LENGTH)
                                    visibilityLabelLogin = !visibilityLabelLogin
                                    visibilityProgressBar = !visibilityProgressBar
                                    statusEnabledCardLogin = true
                                    delay(500)
                                    navToMainScreen(navController)
                                }
                            }
                        }
                    },
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 16.dp
                ),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Green)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AnimatedVisibility(
                        visible = visibilityLabelLogin
                    ) {
                        Text(
                            text = stringResource(id = R.string.login),
                            modifier = Modifier.wrapContentSize(),
                            color = NavyColor,
                            fontFamily = FontFamily(Font(R.font.dana_en_num_bold)),
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 16.sp
                        )
                    }

                    AnimatedVisibility(
                        visible = visibilityProgressBar
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(30.dp),
                            strokeWidth = 4.dp,
                            color = NavyColor,
                            strokeCap = StrokeCap.Round
                        )
                    }
                }
            }
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

fun checkFieldForValidation(
    userName: String,
    password: String,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    context: Context
): Boolean {
    return if (userName.isEmpty() || password.isEmpty()) {
        coroutineScope.launch { snackbarHostState.showSnackbar(context.getString(R.string.please_enter_fields)) }
        false
    } else {
        true
    }
}

fun checkAuthentication(
    userName: String,
    password: String,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    context: Context
) = when {
    (userName == USER_NAME && password == PASSWORD) -> {
        true
    }

    else -> {
        coroutineScope.launch { snackbarHostState.showSnackbar(context.getString(R.string.message_when_username_password_incorrect)) }
        false
    }
}

fun navToMainScreen(navController: NavController) {
    navController.navigate(Screens.MainScreenRoute.route) {
        popUpTo(Screens.LoginScreenRoute.route) { inclusive = true }
    }
}

@Preview(showBackground = true, locale = "en", showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Preview() {
    val loginScreenViewModel =
        LoginScreenViewModel(SharedPreferencesManager(LocalContext.current))
    LoginUi(NavController(LocalContext.current), loginScreenViewModel)
}