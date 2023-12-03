package ir.millennium.sampleprojectcompose.domain.useCase

import ir.millennium.sampleprojectcompose.data.model.remote.ResponseNewsModel
import ir.millennium.sampleprojectcompose.data.repository.remote.RemoteRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class GetNewsUseCase @Inject constructor(private val remoteRepository: RemoteRepositoryImpl) {

    open fun getNews(params: MutableMap<String, Any>): Flow<ResponseNewsModel> {
        return remoteRepository.getNews(params)
    }
}