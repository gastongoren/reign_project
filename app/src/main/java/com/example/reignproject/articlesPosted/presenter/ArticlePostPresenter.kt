package com.example.reignproject.articlesPosted.presenter

import android.content.Context
import com.example.reignproject.articlesPosted.ArticlesPostMVP
import com.example.reignproject.articlesPosted.model.ArticlesPostResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

open class ArticlePostPresenter constructor(
    var context: Context,
    private var repository: ArticlesPostMVP.Repository
) :
    ArticlesPostMVP.Presenter {

    private var subscription: Disposable? = null
    private var view: ArticlesPostMVP.View? = null

    override fun getArticleFromRepository() {
        subscription = repository.getArticlesPost("android")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ onScreenDataFetched(it) }, { onErrorFetchingData() })
    }

    private fun onScreenDataFetched(response: ArticlesPostResponse) {
        subscription?.dispose()
        view?.showArticlePosted(repository.validateArticlePost(response.hits))
    }

    private fun onErrorFetchingData() {
        view?.showErrorMessage("Ups! Houston we have a problem")
    }

    private fun refreshArticles() {
        getArticleFromRepository()
    }

    override fun onPostSelected(storyUrl: String) {
        view?.goToWebView(storyUrl)
    }

    override fun onPostDeleted(objectId: String) {
        repository.deletePost(objectId)
        refreshArticles()
    }

    override fun getView(view: ArticlesPostMVP.View?) {
        this.view = view
    }

}