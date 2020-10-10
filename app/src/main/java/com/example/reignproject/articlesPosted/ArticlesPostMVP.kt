package com.example.reignproject.articlesPosted

import com.example.reignproject.articlesPosted.model.ArticlesPostResponse
import io.reactivex.Single

interface ArticlesPostMVP {
    interface View {
        fun showArticlePosted(articlesPost: List<ArticlesPostResponse.Hits>)
        fun showErrorMessage(message: String)
        fun goToWebView(storyUrl: String)
    }

    interface Presenter {
        fun getArticleFromRepository()
        fun onPostSelected(storyUrl: String)
        fun onPostDeleted(objectId: String)
        fun getView(view: View?)
    }

    interface Repository {
        fun getArticlesPost(device: String): Single<ArticlesPostResponse>
        fun validateArticlePost(list: List<ArticlesPostResponse.Hits>): List<ArticlesPostResponse.Hits>
        fun deletePost(objectId: String)
    }
}