package com.example.timganapp.ui.fragment
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.timganapp.R
import com.example.timganapp.common.Type
import com.example.timganapp.repository.Article
import com.example.timganapp.ui.activity.PhotoActivity
import com.example.timganapp.ui.adapter.GrilAdapter
import com.example.timganapp.util.toast
import kotlinx.android.synthetic.main.activity_article_detail.*
import kotlinx.android.synthetic.main.fragment_article_list.*
import org.jetbrains.anko.support.v4.startActivity

class WelfareFragment : BaseFragment() {
    var adapter: GrilAdapter? = null

    companion object {
        fun getInstance(): WelfareFragment {
            return WelfareFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.visibility = View.VISIBLE
    }

    override fun initRecycleView() {
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        var simpleAnimator: SimpleItemAnimator = recyclerView.itemAnimator as SimpleItemAnimator
        adapter = GrilAdapter(activity?.applicationContext, R.layout.item_girl)
        simpleAnimator.supportsChangeAnimations = false
        adapter?.setOnItemClickListener { adapter, view, position ->
            start2PhotoAcivity(adapter.getItem(position) as Article)
        }
        recyclerView.adapter = adapter
        adapter?.setOnLoadMoreListener({
            pageNumber++
            isRefresh = false
            loadData(pageSize, pageNumber)
        }, recyclerView)
    }

    override fun getType(): String {
        return Type.福利.name
    }

    override fun loadError() {
        activity?.toast("girl load data error")
    }

    override fun loadSuccess(data: List<Article>) {
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

    private fun start2PhotoAcivity(article:Article) {
        startActivity<PhotoActivity>("url" to article.url)
    }
}