package ir.millennium.sampleprojectcompose.data.dataSource.local.preferencesDataStoreManager

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import ir.millennium.sampleprojectcompose.domain.entity.TypeLanguage
import ir.millennium.sampleprojectcompose.domain.entity.TypeTheme
import ir.millennium.sampleprojectcompose.presentation.utils.Constants.USER_PREFERENCES_REPOSITORY
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(USER_PREFERENCES_REPOSITORY)

@Singleton
class UserPreferencesRepository @Inject constructor(@ApplicationContext appContext: Context) {

    private val TAG = USER_PREFERENCES_REPOSITORY

    private val settingsDataStore = appContext.dataStore

    private object PreferencesKeys {
        val STATUS_LOGIN_USER = booleanPreferencesKey("status_login_user")
        val TYPE_THEME = intPreferencesKey("type_theme")
        val LANGUAGE_APP = stringPreferencesKey("language_app")
    }

    suspend fun setStatusLoginUser(statusLoginUser: Boolean) {
        settingsDataStore.edit { preferences ->
            preferences[PreferencesKeys.STATUS_LOGIN_USER] = statusLoginUser
        }
    }

    val isUserLogin = settingsDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Timber.tag(TAG).e(exception, "Error reading preferences.")
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[PreferencesKeys.STATUS_LOGIN_USER] ?: false
        }

    suspend fun setStatusTheme(isLightThemeActive: Int) {
        settingsDataStore.edit { preferences ->
            preferences[PreferencesKeys.TYPE_THEME] = isLightThemeActive
        }
    }

    val statusTheme = settingsDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Timber.tag(TAG).e(exception, "Error reading preferences.")
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[PreferencesKeys.TYPE_THEME] ?: TypeTheme.LIGHT.typeTheme
        }

    suspend fun setLanguageApp(language: String) {
        settingsDataStore.edit { preferences ->
            preferences[PreferencesKeys.LANGUAGE_APP] = language
        }
    }

    val languageApp = settingsDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Timber.tag(TAG).e(exception, "Error reading preferences.")
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[PreferencesKeys.LANGUAGE_APP] ?: TypeLanguage.ENGLISH.typeLanguage
        }
}