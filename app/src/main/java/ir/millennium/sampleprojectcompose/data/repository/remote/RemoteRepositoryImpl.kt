package ir.millennium.sampleprojectcompose.data.repository.remote

import ir.millennium.sampleprojectcompose.data.dataSource.remote.ApiService
import ir.millennium.sampleprojectcompose.data.model.remote.ResponseArticlesModel
import ir.millennium.sampleprojectcompose.domain.repository.remote.RemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteRepositoryImpl(private val apiService: ApiService) : RemoteRepository {

    override fun getArticles(params: MutableMap<String, Any>): Flow<ResponseArticlesModel> = flow {
        val response = apiService.getArticles(params)
        emit(response)
    }
}