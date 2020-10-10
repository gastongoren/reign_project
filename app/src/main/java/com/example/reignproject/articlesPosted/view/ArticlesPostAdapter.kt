package com.example.reignproject.articlesPosted.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.reignproject.R
import com.example.reignproject.articlesPosted.model.ArticlesPostResponse
import kotlinx.android.synthetic.main.articles_post_adapter_item.view.*

class ArticlesPostAdapter(
    private val newsList: List<ArticlesPostResponse.Hits>,
    private val onItemSelected: (storyUrl: String?) -> Unit
) : RecyclerView.Adapter<ArticlesPostAdapter.ViewHolder>() {

    private var mutableArticlesList: MutableList<ArticlesPostResponse.Hits> =
        newsList as MutableList<ArticlesPostResponse.Hits>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent, onItemSelected)
    }

    override fun getItemCount(): Int = mutableArticlesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newsSelected = mutableArticlesList[position]
        holder.bind(newsSelected)
    }

    fun getObjectId(position: Int): String = mutableArticlesList[position].objectId

    fun removePost(position: Int) {
        mutableArticlesList.removeAt(position)
        notifyItemRemoved(position)
    }

    class ViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        private val onItemSelected: (storyUrl: String?) -> Unit
    ) : RecyclerView.ViewHolder(
        inflater.inflate(
            R.layout.articles_post_adapter_item,
            parent,
            false
        )
    ) {
        fun bind(articleSelected: ArticlesPostResponse.Hits) {
            itemView.news_item_title.text = articleSelected.storyTitle ?: articleSelected.title
            itemView.user_name_text.text = articleSelected.author
            itemView.date_text.text = CurrentTime().getDifferenceTime(articleSelected.createdAt)
            itemView.setOnClickListener {
                onItemSelected(articleSelected.storyUrl)
            }
        }
    }
}
