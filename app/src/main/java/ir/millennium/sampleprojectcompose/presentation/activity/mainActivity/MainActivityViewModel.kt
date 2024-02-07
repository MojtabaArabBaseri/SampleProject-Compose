package ir.millennium.sampleprojectcompose.presentation.activity.mainActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.millennium.sampleprojectcompose.data.dataSource.local.preferencesDataStoreManager.UserPreferencesRepository
import ir.millennium.sampleprojectcompose.domain.entity.TypeTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
open class MainActivityViewModel @Inject constructor(
    val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val statusThemeFlow = userPreferencesRepository.statusTheme
    private var _typeTheme = runBlocking { MutableStateFlow(statusThemeFlow.first()) }
    val typeTheme: StateFlow<Int> = _typeTheme

    private val languageAppFlow = userPreferencesRepository.languageApp
    private var _languageApp = runBlocking { MutableStateFlow(languageAppFlow.first()) }
    val languageApp: StateFlow<String> = _languageApp

    private val _authScreen = MutableStateFlow(true)
    val authScreen: StateFlow<Boolean> = _authScreen

    fun onThemeChanged(newTheme: Int) {
        when (newTheme) {
            TypeTheme.LIGHT.typeTheme -> {
                viewModelScope.launch {
                    userPreferencesRepository.setStatusTheme(TypeTheme.LIGHT.typeTheme)
                }
                _typeTheme.value = TypeTheme.LIGHT.typeTheme
            }

            TypeTheme.DARK.typeTheme -> {
                viewModelScope.launch {
                    userPreferencesRepository.setStatusTheme(TypeTheme.DARK.typeTheme)
                }
                _typeTheme.value = TypeTheme.DARK.typeTheme
            }
        }
    }

    fun onAuthScreen(authScreen: Boolean) {
        this._authScreen.value = authScreen
    }
}