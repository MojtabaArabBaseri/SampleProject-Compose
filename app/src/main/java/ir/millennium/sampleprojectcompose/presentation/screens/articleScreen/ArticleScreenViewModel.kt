package ir.millennium.sampleprojectcompose.presentation.screens.articleScreen

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.millennium.sampleprojectcompose.data.dataSource.remote.UiState
import ir.millennium.sampleprojectcompose.data.model.remote.ArticleItem
import ir.millennium.sampleprojectcompose.domain.useCase.GetArticlesUseCase
import ir.millennium.sampleprojectcompose.presentation.utils.Constants.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
open class ArticleScreenViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase
) : ViewModel() {

    val stateLazyColumn = LazyListState()

    private val _isShowLoadingData = MutableStateFlow(false)
    val isShowLoadingData: StateFlow<Boolean> = _isShowLoadingData

    private val _uiState = MutableStateFlow<UiState>(UiState.Initialization)
    val uiState: StateFlow<UiState> = _uiState

    var articleList = mutableStateListOf<ArticleItem>()

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

        getArticlesUseCase.getArticles(params)
            .flowOn(Dispatchers.IO)
            .map { articleList ->
                articleList.articles?.let { this.articleList.addAll(it) }
                _uiState.value = UiState.Success(this.articleList)
            }
            .onStart {
                _uiState.value = UiState.Loading
            }
            .catch { throwable ->
                _uiState.value = UiState.Error(throwable)
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
