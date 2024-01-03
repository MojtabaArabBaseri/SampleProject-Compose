package ir.millennium.sampleprojectcompose.presentation.activity.mainActivity

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.millennium.sampleprojectcompose.data.dataSource.local.sharedPreferences.SharedPreferencesManager
import ir.millennium.sampleprojectcompose.domain.entity.TypeTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
open class MainActivityViewModel @Inject constructor(
    val sharedPreferencesManager: SharedPreferencesManager
) : ViewModel() {

    private val _typeTheme = MutableStateFlow(sharedPreferencesManager.getStatusTheme())
    val typeTheme: StateFlow<Int> = _typeTheme

    private val _authScreen = MutableStateFlow(true)
    val authScreen: StateFlow<Boolean> = _authScreen

    fun onThemeChanged(newTheme: Int) {
        when (newTheme) {
            TypeTheme.LIGHT.typeTheme -> {
                sharedPreferencesManager.setStatusTheme(TypeTheme.LIGHT.typeTheme)
                _typeTheme.value = TypeTheme.LIGHT.typeTheme
            }

            TypeTheme.DARK.typeTheme -> {
                sharedPreferencesManager.setStatusTheme(TypeTheme.DARK.typeTheme)
                _typeTheme.value = TypeTheme.DARK.typeTheme
            }
        }
    }

    fun onAuthScreen(authScreen: Boolean) {
        this._authScreen.value = authScreen
    }
}