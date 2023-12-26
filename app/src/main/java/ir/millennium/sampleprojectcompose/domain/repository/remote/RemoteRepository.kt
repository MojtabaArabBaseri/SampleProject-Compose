package ir.millennium.sampleprojectcompose.domain.repository.remote

import ir.millennium.sampleprojectcompose.data.model.remote.ResponseArticlesModel
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {

    fun getArticles(params: MutableMap<String, Any>): Flow<ResponseArticlesModel>

}