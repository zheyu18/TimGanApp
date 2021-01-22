package com.example.timganapp.ui.adapter

import android.content.Context
import android.text.format.DateUtils
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.timganapp.R
import com.example.timganapp.repository.Article
import kotlinx.android.synthetic.main.item_article.view.*
import java.text.SimpleDateFormat

class ArticleAdapter(var context: Context?, layoutId: Int) :
    BaseQuickAdapter<Article, BaseViewHolder>(layoutId) {
    var date: SimpleDateFormat = SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss")
    override fun convert(helper: BaseViewHolder?, item: Article?) {
        helper?.setText(R.id.title, item?.desc)
        helper?.setText(R.id.who, item?.who)
        helper?.setText(R.id.type, item?.type)
        helper?.setText(
            R.id.publishedAt,
            DateUtils.getRelativeTimeSpanString(date.parse(item?.publishedAt).time)
        )

        var image: ImageView? = helper?.getView(R.id.image)
        if (item?.images == null || item?.images.isEmpty()) {
            image?.visibility = View.GONE
        }else{
            image?.visibility = View.VISIBLE
            val width:Int? = context?.resources?.getDimension(R.dimen.article_image_width)?.toInt()
            Glide.with(context!!).load("${item?.images[0]}?imageView2/0/w/$width").into(image!!)
        }
    }
}