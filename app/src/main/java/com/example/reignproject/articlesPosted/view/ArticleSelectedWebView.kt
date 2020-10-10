package com.example.reignproject.articlesPosted.view

import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.reignproject.R
import com.example.reignproject.articlesPosted.view.ArticlesPostActivity.Companion.STORY_URL
import kotlinx.android.synthetic.main.activity_article_selected_web_view.*


class ArticleSelectedWebView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_selected_web_view)
        setUpActionBar()
        setUpWebView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpActionBar() {
        setSupportActionBar(toolbar)
    }

    private fun setUpWebView() {
        webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        intent?.getStringExtra(STORY_URL)?.let {
            webview.loadUrl(it)
        }
    }
}