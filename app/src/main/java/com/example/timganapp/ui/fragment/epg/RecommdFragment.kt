
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.timganapp.R
import com.example.timganapp.common.Type
import com.example.timganapp.net.Api
import com.example.timganapp.repository.Article
import com.example.timganapp.ui.activity.ArticleDetailActivity
import com.example.timganapp.ui.adapter.RecommendAdapter
import com.example.timganapp.util.toast


import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_recommend.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.json.JSONObject
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class RecommdFragment : Fragment(), AnkoLogger {

    companion object {
        fun newInstance(date: String): RecommdFragment {
            val fragment: RecommdFragment = RecommdFragment()
            val args: Bundle = Bundle()
            if (!date.isEmpty()) {
                args.putString("date", date)
            }
            fragment.arguments = args
            return fragment
        }
    }

    var date: String? = null
    var iamgeUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        date = arguments?.getString("date")
        return inflater.inflate(R.layout.fragment_recommend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadDate()
    }

    fun loadDate() {
        val api = Api.create()
        api.getDataByDate(date!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                setUpView(parseRsult(result.string())!!)
            }
    }

    private fun parseRsult(result: String): List<Article>? {
        val jsonObject = JSONObject(result)
        val error = jsonObject.getBoolean("error")
        if (error) {
            activity?.toast("Service data error!")
            return null
        }
        val results = jsonObject.getJSONObject("results")
        val data: MutableList<Article> = arrayListOf()

        val gson = Gson()

        val type = object : TypeToken<List<Article>>() {}.type
        results.keys().forEach {
            if (it != Type.福利.name) {
                data.addAll(gson.fromJson<List<Article>>(results.getJSONArray(it).toString(), type))
            } else {
                val array = results.getJSONArray(it)
                if (array.length() >= 0) {
                    iamgeUrl = array.getJSONObject(0).getString("url")
                    Log.e("parseRsult", array.toString())
                }
            }
        }
//        data.forEach {
//            info(it)
//        }
        return data
    }

    private fun setUpView(data: List<Article>) {

        Glide.with(activity!!)
            .load(iamgeUrl)
            .error(R.drawable.placeholder)
            .into(welFare)

        val adapter = RecommendAdapter(R.layout.item_recommend, data)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.adapter = adapter

        adapter.onItemClickListener =
            BaseQuickAdapter.OnItemClickListener { adapter, view, position ->

                activity?.toast("item Onclick ${position}")
                start2DetailAcivity(adapter.getItem(position) as Article)
            }
    }

    private fun start2DetailAcivity(article: Article) {
        val intent = Intent(activity, ArticleDetailActivity::class.java)
        intent.putExtra("desc", article.desc)
        intent.putExtra("url", article.url)
        activity?.startActivity(intent)
    }
}
