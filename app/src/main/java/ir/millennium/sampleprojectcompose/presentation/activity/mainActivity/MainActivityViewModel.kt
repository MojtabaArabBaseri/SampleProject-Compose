package ir.millennium.sampleprojectcompose.presentation.activity.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.millennium.sampleprojectcompose.data.dataSource.local.sharedPreferences.SharedPreferencesManager
import ir.millennium.sampleprojectcompose.domain.entity.TypeTheme
import javax.inject.Inject

@HiltViewModel
open class MainActivityViewModel @Inject constructor(
    val sharedPreferencesManager: SharedPreferencesManager
) : ViewModel() {

    private val _theme = MutableLiveData(sharedPreferencesManager.getStatusTheme())
    val typeTheme: LiveData<Int> = _theme

    private val _authScreen = MutableLiveData(true)
    val authScreen: LiveData<Boolean> = _authScreen

    fun onThemeChanged(newTheme: Int) {
        when (newTheme) {
            TypeTheme.LIGHT.typeTheme -> {
                sharedPreferencesManager.setStatusTheme(TypeTheme.LIGHT.typeTheme)
                _theme.value = TypeTheme.LIGHT.typeTheme
            }

            TypeTheme.DARK.typeTheme -> {
                sharedPreferencesManager.setStatusTheme(TypeTheme.DARK.typeTheme)
                _theme.value = TypeTheme.DARK.typeTheme
            }
        }
    }

    fun onAuthScreen(authScreen: Boolean) {
        _authScreen.value = authScreen
    }
}