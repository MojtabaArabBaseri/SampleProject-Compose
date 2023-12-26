package ir.millennium.sampleprojectcompose.presentation.activity.mainActivity

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.millennium.sampleprojectcompose.data.dataSource.local.sharedPreferences.SharedPreferencesManager
import ir.millennium.sampleprojectcompose.domain.entity.TypeTheme
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
open class MainActivityViewModel @Inject constructor(
    val sharedPreferencesManager: SharedPreferencesManager
) : ViewModel() {

    val typeTheme = MutableStateFlow(sharedPreferencesManager.getStatusTheme())

    val authScreen = MutableStateFlow(true)

    fun onThemeChanged(newTheme: Int) {
        when (newTheme) {
            TypeTheme.LIGHT.typeTheme -> {
                sharedPreferencesManager.setStatusTheme(TypeTheme.LIGHT.typeTheme)
                typeTheme.value = TypeTheme.LIGHT.typeTheme
            }

            TypeTheme.DARK.typeTheme -> {
                sharedPreferencesManager.setStatusTheme(TypeTheme.DARK.typeTheme)
                typeTheme.value = TypeTheme.DARK.typeTheme
            }
        }
    }

    fun onAuthScreen(authScreen: Boolean) {
        this.authScreen.value = authScreen
    }
}