package com.nytimes.popular_articles.ui.article_details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.islamassem.utils.base.BaseActivity
import com.islamassem.utils.base.BaseFragment
import com.islamassem.utils.hide
import com.islamassem.utils.loadImage
import com.nytimes.popular_articles.R
import com.nytimes.popular_articles.databinding.FragmentArticleDetailsBinding
import com.nytimes.popular_articles.models.Article
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetails : BaseFragment() {
    lateinit var binding: FragmentArticleDetailsBinding
    private lateinit var article: Article
    private val safeArgs by navArgs<ArticleDetailsArgs>()
    override fun doBinding(container: ViewGroup?, inflater: LayoutInflater): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_article_details, container, false)
        return binding.root
    }

    override fun initViews() {
        article = safeArgs.article
        if (article.media.isNotEmpty())
            loadImage(
                binding.image,
                article.media[0].mediaMetadata.find {
                    it.format == "mediumThreeByTwo440"
                }?.url,
                binding.progressBar
            ) else
            binding.progressBar.hide()
        binding.title.text = article.title
        binding.content.text = article.jsonMemberAbstract
        binding.date.text = article.publishedDate
        binding.by.text = article.byline
        binding.section.text = "Sectoin : ${article.section}"
    }

    override fun saveInstanceState(savedInstanceState: Bundle) {

    }

    override fun initData(data: Bundle) {
    }

    override fun initVariables(context: Context) {

    }

    override fun initViewModel() {

    }
}