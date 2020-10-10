package com.example.reignproject.articlesPosted.network

import com.example.reignproject.BuildConfig
import com.example.reignproject.articlesPosted.model.ArticlesPostResponse
import com.google.gson.GsonBuilder
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticlePostApiservice {
    @GET("search_by_date")
    fun getRecentPost(@Query("query") device: String): Single<ArticlesPostResponse>
}

interface RetrofitClient {
    fun getRecentPost(device: String): Single<ArticlesPostResponse>
}

class RetrofitClientImpl : RetrofitClient {
    private var retrofit: Retrofit? = null
    private val BASE_URL = "https://hn.algolia.com/api/v1/"

    val client = OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        })
        .build()

    private fun getClient(baseUrl: String): Retrofit {
        if (retrofit == null) {
            val builder = GsonBuilder()
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(builder.create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
        return retrofit!!
    }

    override fun getRecentPost(device: String): Single<ArticlesPostResponse> {
        return getClient(BASE_URL).create(ArticlePostApiservice::class.java).getRecentPost(device)
    }

}