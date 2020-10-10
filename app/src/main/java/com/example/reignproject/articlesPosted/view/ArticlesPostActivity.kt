package com.example.reignproject.articlesPosted.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reignproject.R
import com.example.reignproject.articlesPosted.ArticlesPostMVP
import com.example.reignproject.articlesPosted.di.ArticlesPostDependenciesAssembler
import com.example.reignproject.articlesPosted.model.ArticlesPostResponse
import com.example.reignproject.commons.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.activity_articles_post.*


class ArticlesPostActivity : ArticlesPostMVP.View, AppCompatActivity() {

    var presenter: ArticlesPostMVP.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles_post)
        initializePresenter()
        refreshLayout()
    }

    private fun initializePresenter() {
        presenter = ArticlesPostDependenciesAssembler().providesArticlesPostPresenter(this).also {
            it.getView(this)
            it.getArticleFromRepository()
        }
    }

    private fun refreshLayout() {
        swipe_refresh_layout.setOnRefreshListener {
            presenter?.getArticleFromRepository()
        }
    }

    override fun showArticlePosted(articlesPost: List<ArticlesPostResponse.Hits>) {
        swipe_refresh_layout.isRefreshing = false
        setupArticlesRecyclerView(articlesPost)
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun goToWebView(storyUrl: String) {
        val intent = Intent(this, ArticleSelectedWebView::class.java)
        intent.putExtra(STORY_URL, storyUrl)
        startActivity(intent)
    }

    private fun setupArticlesRecyclerView(articlesPost: List<ArticlesPostResponse.Hits>) {
        val articlesPostAdapter = ArticlesPostAdapter(articlesPost) { articleUrl ->
            articleUrl?.let {
                presenter?.onPostSelected(it)
            }
        }
        articlesPostAdapter.notifyDataSetChanged()
        news_recycler_view.apply {
            val dividerItemDecoration = DividerItemDecoration(
                this.context,
                1
            )
            this.addItemDecoration(dividerItemDecoration)
            this.adapter = articlesPostAdapter
            this.layoutManager = LinearLayoutManager(this@ArticlesPostActivity)
        }

        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                presenter?.onPostDeleted(articlesPostAdapter.getObjectId(viewHolder.adapterPosition))
                articlesPostAdapter.removePost(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(news_recycler_view)
    }

    companion object {
        const val STORY_URL = "storyUrl"
    }
}
