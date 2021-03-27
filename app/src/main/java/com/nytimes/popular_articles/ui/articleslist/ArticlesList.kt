package com.nytimes.popular_articles.ui.articleslist

import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.nytimes.popular_articles.data.Resource
import com.islamassem.utils.base.BaseFragment
import com.nytimes.popular_articles.R
import com.nytimes.popular_articles.databinding.FragmentArticlesListBinding
import com.nytimes.popular_articles.ui.ArticlesListViewModel
import com.islamassem.utils.show
import com.islamassem.utils.hide
import com.islamassem.utils.loadImage
import com.islamassem.utils.recyclerview.BaseAdapter
import com.islamassem.utils.recyclerview.OnItemClickListener
import com.nytimes.popular_articles.models.Article
import com.nytimes.popular_articles.ui.article_details.ArticleDetailsArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticlesList : BaseFragment() {
    lateinit var binding: FragmentArticlesListBinding
    override fun doBinding(container: ViewGroup?, inflater: LayoutInflater): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_articles_list, container, false)
        return binding.root
    }

    private val viewModel: ArticlesListViewModel by viewModels()
    private val adapter by lazy {
        ArticlesAdapter(object : OnItemClickListener<Article, View?>() {
            override fun onItemClick(article: Article, v: View?, position: Int) {
                super.onItemClick(article, v, position)
                val action = ArticlesListDirections.actionArticleDetails(article, article.title)
                NavHostFragment.findNavController(this@ArticlesList).navigate(action)
            }

        })
    }

    override fun initViews() {
        binding.recyclerView.adapter = adapter
        binding.error.setOnClickListener { viewModel.getArticles() }
        binding.empty.setOnClickListener { viewModel.getArticles() }
        if (adapter.itemCount == 0)
            viewModel.getArticles()
    }

    override fun saveInstanceState(savedInstanceState: Bundle) {
    }

    override fun initData(data: Bundle) {
    }

    override fun initVariables(context: Context) {
    }

    override fun initViewModel() {
        viewModel._articlesLiveData.observe(this) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.loadingView.show()
                    binding.loadingView.start()
                }
                Resource.Status.ERROR -> {
                    messagesHandler.showMessageDialog(it.message.toString())
                    binding.loadingView.hide()
                    binding.loadingView.stop()
                    if (it.data == null && adapter.itemCount == 0)
                        binding.error.show()
                }
                Resource.Status.SUCCESS -> {
                    messagesHandler.hideDialog()
                    binding.loadingView.hide()
                    binding.loadingView.stop()
                    it.data?.let { it2 -> adapter.addItems(it2.results) }
                    if (it.data == null)
                        binding.error.show()
                    else {
                        binding.error.hide()
                        if (adapter.itemCount == 0) binding.empty.show() else binding.empty.hide()

                    }
                }
            }
        }
    }
}