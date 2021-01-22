package com.example.timganapp.ui.fragment.epg

import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.timganapp.R
import com.example.timganapp.repository.Article
import com.example.timganapp.ui.activity.ArticleDetailActivity
import com.example.timganapp.ui.adapter.ArticleAdapter
import com.example.timganapp.ui.fragment.BaseFragment
import com.example.timganapp.util.toast
import kotlinx.android.synthetic.main.fragment_article_list.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.startActivity

open abstract class ArticleFragment : BaseFragment(), AnkoLogger {
    var adapter: ArticleAdapter? = null

    override fun initRecycleView() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = ArticleAdapter(activity?.applicationContext, R.layout.item_article)
        recyclerView.adapter = adapter

        adapter?.setOnLoadMoreListener({ loadMore() }, recyclerView)
        adapter?.onItemClickListener =
            BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                start2DetailAcivity(adapter.data[position] as Article)
            }
    }

    private fun loadMore() {
        pageNumber++
        isRefresh = false
        loadData(pageSize, pageNumber)
    }

    override fun loadError() {
        activity?.toast("LoadData Error")
    }

    override fun loadSuccess(data: List<Article>) {
        info(" loadSuccess : " + data.toString())
        if (isRefresh) {
            adapter?.setNewData(data)
        } else {
            adapter?.addData(data)
        }
    }

    override fun loadFinish() {
        if (swipeLayout.isRefreshing) {
            swipeLayout.isRefreshing = false
        }
        adapter?.loadMoreComplete()
    }

    private fun start2DetailAcivity(article: Article) {
        startActivity<ArticleDetailActivity>("desc" to article.desc,
            "url" to article.url)
    }
}