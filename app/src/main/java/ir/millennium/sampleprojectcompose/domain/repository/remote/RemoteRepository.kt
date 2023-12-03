package ir.millennium.sampleprojectcompose.domain.repository.remote

import ir.millennium.sampleprojectcompose.data.model.remote.ResponseNewsModel
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {

    fun getNews(params: MutableMap<String, Any>): Flow<ResponseNewsModel>

}