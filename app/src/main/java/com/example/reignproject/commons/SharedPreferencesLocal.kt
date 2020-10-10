package com.example.reignproject.commons

import android.content.Context
import android.content.SharedPreferences
import com.example.reignproject.articlesPosted.model.ArticlesPostResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class SharedPreferencesLocal(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("localStorage", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun setArticleResponse(articleResponse: ArticlesPostResponse) {
        val articlesSaved = Gson().toJson(articleResponse)
        editor.putString(ARTICLE_RESPONSE_SAVED, articlesSaved)
        editor.apply()
    }

    fun getSavedArticleResponse(): ArticlesPostResponse {
        val articlesPostResponse: String =
            sharedPreferences.getString(ARTICLE_RESPONSE_SAVED, "")!!
        return Gson().fromJson(articlesPostResponse, ArticlesPostResponse::class.java)
    }

    fun setObjectIdDeletedPost(articleResponse: ArticlesPostResponse.Hits) {
        var list: MutableList<ArticlesPostResponse.Hits> = mutableListOf()
        getObjectIdDeletedPost()?.let {
            list = it as MutableList
        }
        list.add(articleResponse)
        val jsonText: String = Gson().toJson(list)
        editor.putString(LIST, jsonText)
        editor.apply()
    }

    fun getObjectIdDeletedPost(): List<ArticlesPostResponse.Hits>? {
        var list: String? = sharedPreferences.getString(LIST, "")
        val type: Type = object : TypeToken<List<ArticlesPostResponse.Hits?>?>() {}.type
        list?.ifEmpty {
            return null
        }
        return Gson().fromJson(list, type)
    }

    companion object {
        const val ARTICLE_RESPONSE_SAVED = "articleResponseSaved"
        const val LIST = "list"
    }
}