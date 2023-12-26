package ir.millennium.sampleprojectcompose.domain.useCase

import ir.millennium.sampleprojectcompose.data.model.remote.ResponseArticlesModel
import ir.millennium.sampleprojectcompose.data.repository.remote.RemoteRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class GetArticlesUseCase @Inject constructor(private val remoteRepository: RemoteRepositoryImpl) {

    open fun getArticles(params: MutableMap<String, Any>): Flow<ResponseArticlesModel> {
        return remoteRepository.getArticles(params)
    }
}