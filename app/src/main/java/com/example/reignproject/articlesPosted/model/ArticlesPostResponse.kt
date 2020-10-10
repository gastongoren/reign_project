package com.example.reignproject.articlesPosted.model

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class ArticlesPostResponse(
    val hits: List<Hits>
) {
    @Keep
    data class Hits(
        @SerializedName("story_id")
        @Expose
        val storyId: String,
        @SerializedName("story_title")
        @Expose
        val storyTitle: String?,
        @SerializedName("title")
        @Expose
        val title: String,
        @SerializedName("story_url")
        @Expose
        val storyUrl: String?,
        val author: String,
        @SerializedName("created_at")
        @Expose
        val createdAt: String,
        @SerializedName("objectID")
        @Expose
        val objectId: String
    )
}