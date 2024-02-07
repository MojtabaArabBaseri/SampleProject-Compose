package ir.millennium.sampleprojectcompose.presentation.screens.homeScreen

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class HomeScreenViewModel @Inject constructor() : ViewModel() {

    val stateLazyColumn = LazyListState()

}