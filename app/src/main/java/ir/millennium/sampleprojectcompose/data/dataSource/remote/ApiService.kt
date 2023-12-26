package ir.millennium.sampleprojectcompose.data.dataSource.remote

import ir.millennium.sampleprojectcompose.data.model.remote.ResponseArticlesModel
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {

    @GET("everything")
    suspend fun getArticles(@QueryMap parameters: MutableMap<String, Any>): ResponseArticlesModel

}