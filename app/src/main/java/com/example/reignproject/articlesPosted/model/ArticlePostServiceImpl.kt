package com.example.reignproject.articlesPosted.model

import com.example.reignproject.articlesPosted.network.RetrofitClient
import io.reactivex.Single

interface ArticlePostService {
    fun getArticlePost(device: String): Single<ArticlesPostResponse>
}

class ArticlePostServiceImpl(private val retrofitClient: RetrofitClient) : ArticlePostService {
    override fun getArticlePost(device: String): Single<ArticlesPostResponse> {
        return retrofitClient.getRecentPost(device)
    }
}
