package ir.millennium.sampleprojectcompose.presentation.screens.homeScreen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.millennium.sampleprojectcompose.data.dataSource.local.sharedPreferences.SharedPreferencesManager
import javax.inject.Inject

@HiltViewModel
open class HomeScreenViewModel @Inject constructor(
    val sharedPreferencesManager: SharedPreferencesManager
) : ViewModel()