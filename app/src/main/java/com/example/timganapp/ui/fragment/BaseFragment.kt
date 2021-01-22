package com.example.timganapp.ui.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.timganapp.R
import com.example.timganapp.net.Api
import com.example.timganapp.repository.Article
import com.example.timganapp.repository.Result
import kotlinx.android.synthetic.main.fragment_base.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

abstract class BaseFragment : Fragment() {
    val pageSize = 10
    var pageNumber = 1
    var isRefresh = false
    var activity: Activity? = null
    var rootView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_base, container, false)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleView()
        swipeLayout.setOnRefreshListener {
            pageNumber = 1
            isRefresh = true
            loadData(pageSize, pageNumber)
        }
        loadData(pageSize,pageNumber)
    }

    protected fun loadData(pageSize: Int, pageNumber: Int) {
            Api.create().getData(getType(), pageSize, pageNumber)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                parseResult(result)
            }, {
                loadError()
                loadFinish()
            }, {})
    }

    private fun parseResult(result: Result) {
        if (result.error) {
            loadError()
        } else {
            loadSuccess(result.results)
        }
        loadFinish()
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        this.activity = activity
    }


    override fun onDestroy() {
        super.onDestroy()
        this.activity = null
    }

    abstract fun initRecycleView()

    abstract fun getType(): String

    abstract fun loadError()

    abstract fun loadSuccess(data: List<Article>)

    abstract fun loadFinish()

}