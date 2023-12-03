package ir.millennium.sampleprojectcompose.domain.repository.local

import ir.millennium.sampleprojectcompose.data.model.local.formUnregistered.NewsModel

interface LocalRepository {

    suspend fun getNewsList(): List<NewsModel>

    suspend fun saveToDatabaseNews(newsModel: NewsModel)

    fun clearDatabase()

}