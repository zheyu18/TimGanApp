package com.example.timganapp.ui.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.timganapp.R
import com.example.timganapp.net.Api
import com.example.timganapp.repository.History
import com.example.timganapp.ui.activity.HistoryActivity
import com.example.timganapp.ui.activity.MainActivity
import com.example.timganapp.ui.adapter.HistoryAdapter
import com.example.timganapp.util.dimssProgress
import com.example.timganapp.util.showProgress
import kotlinx.android.synthetic.main.fragment_history.*
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class HistoryFragment : Fragment() {
    var activity: MainActivity? = null

    companion object {
        fun newInstance(): HistoryFragment {
            return HistoryFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        loadPublishedData()
    }

    // 获取数据，并刷新页面
    private fun loadPublishedData() {
        activity?.showProgress()
        val api = Api.create()
        api.getHistory()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    setUpRecyclerView(parseHtml(it))
                }, { Log.d("tim", "history data error") }, {
                    activity?.dimssProgress()
                }
            )
    }

    private fun setUpRecyclerView(data: List<History>) {
        val adapter = HistoryAdapter(R.layout.item_history, data)
        adapter.setOnItemClickListener { adapter, view, position ->
            //            activity?.toast("History Item Click ${position}")
            var history :History = adapter.getItem(position) as History
            start2HistoryAcivity(history.date)
        }
        recyclerView.adapter = adapter
    }

    private fun parseHtml(html: String): List<History> {
        val doc: Document = Jsoup.parse(html)
        val typo: Elements = doc.getElementsByClass("typo")
        val data: MutableList<History> = arrayListOf()
        var history: History
        typo.select("a").listIterator().forEach {
            history = History(it.attr("href").substring(1), it.text())
            data.add(history)
        }
        return data
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        this.activity = context as MainActivity?
    }

    override fun onDetach() {
        super.onDetach()
        this.activity = null
    }

    private fun start2HistoryAcivity(date: String) {
        val intent = Intent(activity, HistoryActivity::class.java)
        intent.putExtra("date",date)
        activity?.startActivity(intent)
    }
}