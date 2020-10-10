package com.example.reignproject.articlesPosted.di

import android.content.Context
import com.example.reignproject.articlesPosted.ArticlesPostMVP
import com.example.reignproject.articlesPosted.model.ArticlePostRepository
import com.example.reignproject.articlesPosted.model.ArticlePostService
import com.example.reignproject.articlesPosted.model.ArticlePostServiceImpl
import com.example.reignproject.articlesPosted.network.RetrofitClient
import com.example.reignproject.articlesPosted.network.RetrofitClientImpl
import com.example.reignproject.articlesPosted.presenter.ArticlePostPresenter

class ArticlesPostDependenciesAssembler {
    fun providesArticlesPostPresenter(context: Context): ArticlesPostMVP.Presenter {
        return ArticlePostPresenter(context, providesArticlesPostRepository(context))
    }

    private fun providesArticlesPostRepository(context: Context): ArticlesPostMVP.Repository {
        return ArticlePostRepository(context, providesArticlesPostService())
    }

    private fun providesArticlesPostService(): ArticlePostService {
        return ArticlePostServiceImpl(providesRetrofitClient())
    }

    private fun providesRetrofitClient(): RetrofitClient {
        return RetrofitClientImpl()
    }
}