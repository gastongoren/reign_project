package com.example.reignproject.articlesPosted.model

import android.content.Context
import com.example.reignproject.articlesPosted.ArticlesPostMVP
import com.example.reignproject.articlesPosted.network.NetworkAvailable
import com.example.reignproject.commons.SharedPreferencesLocal
import io.reactivex.Single


class ArticlePostRepository(context: Context, private val articlePostService: ArticlePostService) :
    ArticlesPostMVP.Repository {

    private var networkAvailable = NetworkAvailable().isNetworkAvailable(context)
    private var sharedPreferencesLocal = SharedPreferencesLocal(context)

    override fun getArticlesPost(device: String): Single<ArticlesPostResponse> {
        return if (networkAvailable) {
            articlePostService.getArticlePost(device).doOnSuccess {
                saveArticleResponse(it)
            }
        } else {
            getArticleResponse()
        }
    }

    override fun validateArticlePost(list: List<ArticlesPostResponse.Hits>): List<ArticlesPostResponse.Hits> {
        val articleDeleted = sharedPreferencesLocal.getObjectIdDeletedPost()
        val mutableList = list as MutableList
        articleDeleted?.forEach { article ->
            list.find {
                it.objectId == article.objectId
            }?.let {
                mutableList.remove(it)
            }
        }
        return mutableList
    }

    override fun deletePost(objectId: String) {
        val savedList = sharedPreferencesLocal.getSavedArticleResponse()
        savedList.hits.find {
            it.objectId == objectId
        }?.let {
            sharedPreferencesLocal.setObjectIdDeletedPost(it)
        }
    }

    private fun saveArticleResponse(articleResponse: ArticlesPostResponse? = null): Single<ArticlesPostResponse> {
        articleResponse?.let {
            sharedPreferencesLocal.setArticleResponse(it)
        }
        return Single.just(articleResponse)
    }

    private fun getArticleResponse(): Single<ArticlesPostResponse> {
        return Single.just(sharedPreferencesLocal.getSavedArticleResponse())
    }
}