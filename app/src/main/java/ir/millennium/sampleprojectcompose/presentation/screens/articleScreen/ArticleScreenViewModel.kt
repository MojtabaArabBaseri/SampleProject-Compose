package ir.millennium.sampleprojectcompose.presentation.screens.articleScreen

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.millennium.sampleprojectcompose.data.dataSource.remote.UiState
import ir.millennium.sampleprojectcompose.data.model.remote.ArticleItem
import ir.millennium.sampleprojectcompose.domain.useCase.GetNewsUseCase
import ir.millennium.sampleprojectcompose.presentation.utils.Constants.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
open class ArticleScreenViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
) : ViewModel() {

    val stateLazyColumn = LazyListState()

    private val _isShowLoadingData = MutableLiveData(false)
    val isShowLoadingData: LiveData<Boolean> = _isShowLoadingData

    var articleList = mutableStateListOf<ArticleItem>()

    val uiState = MutableStateFlow<UiState>(UiState.Initialization)

    private var currentPage = 1

    val params = mutableMapOf<String, Any>()

    init {
        params["apiKey"] = API_KEY
        params["from"] = "2023-07-00"
        params["q"] = "tesla"
        params["page"] = currentPage
    }

    fun getNews(params: MutableMap<String, Any>) {

        params.replace("page", currentPage)

        getNewsUseCase.getNews(params)
            .flowOn(Dispatchers.IO)
            .map { newsList ->
                newsList.articles?.let { articleList.addAll(it) }
                uiState.value = UiState.Success(articleList)
            }
            .onStart {
                uiState.value = UiState.Loading
            }
            .catch { throwable ->
                uiState.value = UiState.Error(throwable)
            }.launchIn(viewModelScope)
    }

    fun refresh() {
        currentPage = 1
        articleList.clear()
        getNews(params)
    }

    fun getNextPage() {
        currentPage++
        getNews(params)
    }

    fun isShowLoadingData(isShow: Boolean) {
        _isShowLoadingData.value = isShow
    }
}