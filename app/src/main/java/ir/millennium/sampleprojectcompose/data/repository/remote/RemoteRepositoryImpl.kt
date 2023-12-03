package ir.millennium.sampleprojectcompose.data.repository.remote

import ir.millennium.sampleprojectcompose.data.dataSource.remote.ApiService
import ir.millennium.sampleprojectcompose.data.model.remote.ResponseNewsModel
import ir.millennium.sampleprojectcompose.domain.repository.remote.RemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteRepositoryImpl(private val apiService: ApiService) : RemoteRepository {

    override fun getNews(params: MutableMap<String, Any>): Flow<ResponseNewsModel> = flow {
        val response = apiService.getNews(params)
        emit(response)
    }
}