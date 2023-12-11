package ir.millennium.sampleprojectcompose.presentation.screens.articleScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ir.millennium.sampleprojectcompose.data.dataSource.remote.UiState
import ir.millennium.sampleprojectcompose.presentation.utils.OnBottomReached
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ArticleScreen(
    navController: NavController,
    articleScreenViewModel: ArticleScreenViewModel
) {

    val isShowLoadingData = articleScreenViewModel.isShowLoadingData.observeAsState(false)

    val swipeRefreshState = rememberSwipeRefreshState(false)

    val stateLazyColumn = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }

    Box(modifier = Modifier.fillMaxSize()) {

        SwipeRefresh(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            state = swipeRefreshState,
            indicator = { state, trigger ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = trigger,
                    scale = true,
                    backgroundColor = MaterialTheme.colorScheme.onBackground,
                    contentColor = MaterialTheme.colorScheme.background
                )
            },
            indicatorPadding = PaddingValues(55.dp),
            onRefresh = { articleScreenViewModel.refresh() }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = stateLazyColumn,
                contentPadding = PaddingValues(top = 65.dp)
            ) {
                items(items = articleScreenViewModel.articleList) { articleItem ->
                    rowArticle(navController, articleItem)
                }

                if (isShowLoadingData.value && articleScreenViewModel.articleList.size > 0) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(16.dp)
                                    .align(Alignment.Center),
                                color = MaterialTheme.colorScheme.primary,
                                strokeWidth = 2.dp
                            )
                        }
                    }
                }
            }
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }

    stateLazyColumn.OnBottomReached {
        if (!isShowLoadingData.value) {
            articleScreenViewModel.isShowLoadingData(true)
            articleScreenViewModel.getNextPage()
        }
    }

    renderUi(
        articleScreenViewModel,
        coroutineScope,
        snackbarHostState,
        isShowLoadingData,
        swipeRefreshState
    )

    getData(articleScreenViewModel, coroutineScope)

}

@Composable
fun getData(articleScreenViewModel: ArticleScreenViewModel, coroutineScope: CoroutineScope) {
    LaunchedEffect(coroutineScope) {
        if (articleScreenViewModel.articleList.size == 0) {
            articleScreenViewModel.getNews(articleScreenViewModel.params)
        }
    }
}

@Composable
fun renderUi(
    articleScreenViewModel: ArticleScreenViewModel,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    isShowLoadingData: State<Boolean>,
    swipeRefreshState: SwipeRefreshState
) {
    LaunchedEffect(coroutineScope) {
        articleScreenViewModel.uiState.collect { dataResource ->
            when (dataResource) {
                is UiState.Loading -> {
                    if (!isShowLoadingData.value) {
                        swipeRefreshState.isRefreshing = true
                    }
                }

                is UiState.Success -> {
                    if (isShowLoadingData.value) {
                        articleScreenViewModel.isShowLoadingData(false)
                    } else {
                        swipeRefreshState.isRefreshing = false
                    }
                }

                is UiState.Error -> {
                    if (swipeRefreshState.isRefreshing) {
                        swipeRefreshState.isRefreshing = false
                    }
                    coroutineScope.launch {
                        val snackbarResult = snackbarHostState.showSnackbar(
                            dataResource.throwable.message.toString(),
                            "retry"
                        )
                        when (snackbarResult) {
                            SnackbarResult.Dismissed -> {}
                            SnackbarResult.ActionPerformed -> {
                                articleScreenViewModel.getNews(articleScreenViewModel.params)
                            }
                        }
                    }
                }

                else -> {}
            }
        }
    }
}
