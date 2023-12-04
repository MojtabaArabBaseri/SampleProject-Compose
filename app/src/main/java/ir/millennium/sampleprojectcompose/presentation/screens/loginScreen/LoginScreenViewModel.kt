package ir.millennium.sampleprojectcompose.presentation.screens.loginScreen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.millennium.sampleprojectcompose.data.dataSource.local.sharedPreferences.SharedPreferencesManager
import javax.inject.Inject

@HiltViewModel
open class LoginScreenViewModel @Inject constructor(
    val sharedPreferencesManager: SharedPreferencesManager
) : ViewModel() {

//    var userNameValue by mutableStateOf("")
//    var passwordValue by mutableStateOf("")
//    var visibilityLabelLogin by mutableStateOf(true)
//    var visibilityProgressBar by mutableStateOf(false)
//    var statusEnabledCardLogin by mutableStateOf(true)
//    var isCorrectUserName by mutableStateOf(false)
//    var isCorrectPassword by mutableStateOf(false)


}