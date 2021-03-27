package com.nytimes.popular_articles.ui.articleslist

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.islamassem.utils.loadImage
import com.islamassem.utils.recyclerview.BaseAdapter
import com.islamassem.utils.recyclerview.BaseViewHolder
import com.islamassem.utils.recyclerview.OnItemClickListener
import com.nytimes.popular_articles.R
import com.nytimes.popular_articles.models.Article

class ArticlesAdapter(
    onItemClickListener: OnItemClickListener<Article, View?>
) : BaseAdapter<Article, ArticleViewHolder>(onItemClickListener) {
    companion object {
        const val ARTICLE_WITH_MEDIA = 100
        const val ARTICLE_NO_MEDIA = 101
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        if (viewType == ARTICLE_WITH_MEDIA)
            return ArticleViewHolder(parent, R.layout.recycler_article, this)
        return ArticleViewHolderNoImage(parent, R.layout.recycler_article_no_media, this)

    }

    override fun getItemViewType(position: Int): Int {
        if (getItem(position)!!.media.isNullOrEmpty())
            return ARTICLE_NO_MEDIA
        return ARTICLE_WITH_MEDIA
    }
}

open class ArticleViewHolder(
    parent: ViewGroup,
    @LayoutRes res: Int,
    adapter: BaseAdapter<Article, *>
) :
    BaseViewHolder<Article>(parent, res, adapter) {
    override fun bind(article: Article, onTClickListener: OnItemClickListener<Article, View?>?) {
        loadImage(
            itemView.findViewById(R.id.image),
            article.media[0].mediaMetadata.find {
                it.format == "mediumThreeByTwo210"
            }?.url,
            itemView.findViewById(R.id.progressBar)
        )
        itemView.findViewById<TextView>(R.id.title).text = article.title
        itemView.findViewById<TextView>(R.id.content).text = article.jsonMemberAbstract
        itemView.findViewById<TextView>(R.id.by).text = article.byline
        itemView.findViewById<TextView>(R.id.date).text = article.publishedDate
        itemView.setOnClickListener { onTClickListener!!.onItemClick(article,itemView,adapterPosition) }
    }
}

class ArticleViewHolderNoImage(
    parent: ViewGroup,
    @LayoutRes res: Int,
    adapter: BaseAdapter<Article, *>
) :
    ArticleViewHolder(parent, res, adapter) {
    override fun bind(article: Article, onTClickListener: OnItemClickListener<Article, View?>?) {
        itemView.findViewById<TextView>(R.id.title).text = article.title
        itemView.findViewById<TextView>(R.id.content).text = article.jsonMemberAbstract
        itemView.findViewById<TextView>(R.id.by).text = article.byline
        itemView.findViewById<TextView>(R.id.date).text = article.publishedDate
        itemView.setOnClickListener { onTClickListener!!.onItemClick(article,itemView,adapterPosition) }
    }
}