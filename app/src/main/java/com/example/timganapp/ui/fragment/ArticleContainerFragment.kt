package com.example.timganapp.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.timganapp.R
import com.example.timganapp.net.Api
import com.example.timganapp.repository.PublishedDate
import com.example.timganapp.ui.activity.MainActivity
import com.example.timganapp.ui.adapter.MainAdapter
import com.example.timganapp.ui.fragment.epg.AndriodFragment
import com.example.timganapp.ui.fragment.epg.IOSFragmet
import com.example.timganapp.ui.fragment.epg.VideoFragmet
import com.example.timganapp.ui.fragment.epg.WebFragmet
import com.example.timganapp.util.dimssProgress
import com.example.timganapp.util.showProgress
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_article_container.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ArticleContainerFragment : Fragment() {
    var published: String? = null
    var activity: MainActivity? = null

    companion object {
        fun newInstance(): ArticleContainerFragment {
            return ArticleContainerFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_article_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadPublishedDate()
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        this.activity = activity as MainActivity
    }

    private fun loadPublishedDate() {
        activity?.showProgress()
        val api = Api.create()
        api.getPublishedDate()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                parseResult(it)
            }, {}, {
                activity?.dimssProgress()
                setUpView()
            })
    }

    private fun parseResult(result: PublishedDate) {
        if (result.error || result.results.isEmpty()) {
            published = SimpleDateFormat("yyy/MM/dd").format(Date())
            return
        }
        published = result.results[0].replace("-", "/")
        Log.e("parseResult",published)
    }

    private fun setUpView() {
        val framgments =ArrayList<Fragment>()

        framgments.add(RecommdFragment.newInstance(published!!))
        framgments.add(AndriodFragment.newInstance())
        framgments.add(IOSFragmet.newInstance())
        framgments.add(WebFragmet.newInstance())
        framgments.add(VideoFragmet.newInstance())
        framgments.add(ExpendFragmet.newInstance())

        val title = resources.getStringArray(R.array.title)
        viewPager.adapter = MainAdapter(framgments, title, childFragmentManager)
        viewPager.offscreenPageLimit = 6

        tabLayout.setupWithViewPager(viewPager)
        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.setCurrentItem(tab!!.position, false)
            }

        })

    }


}