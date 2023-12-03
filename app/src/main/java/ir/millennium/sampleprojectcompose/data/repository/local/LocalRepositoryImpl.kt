package ir.millennium.sampleprojectcompose.data.repository.local

import ir.millennium.sampleprojectcompose.data.dataSource.local.database.RoomServiceDao
import ir.millennium.sampleprojectcompose.data.mapper.mapToEntity
import ir.millennium.sampleprojectcompose.data.mapper.mapToUiModelList
import ir.millennium.sampleprojectcompose.data.model.local.formUnregistered.NewsModel
import ir.millennium.sampleprojectcompose.domain.repository.local.LocalRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private var database: RoomServiceDao,
    private val applicationScope: CoroutineScope
) : LocalRepository {
    override suspend fun getNewsList(): List<NewsModel> {
        return database.getAndroidVersions().mapToUiModelList()
    }

    override suspend fun saveToDatabaseNews(newsModel: NewsModel) {
        return withContext(applicationScope.coroutineContext) {
            database.insert(newsModel.mapToEntity())
        }
    }

    override fun clearDatabase() {
        applicationScope.launch {
            database.clear()
        }
    }
}