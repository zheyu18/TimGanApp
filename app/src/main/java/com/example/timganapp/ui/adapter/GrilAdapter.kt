package com.example.timganapp.ui.adapter

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.timganapp.R
import com.example.timganapp.repository.Article

class GrilAdapter(var context: Context?, layoutId: Int) :
    BaseQuickAdapter<Article, BaseViewHolder>(layoutId) {
    override fun convert(helper: BaseViewHolder, item: Article?) {
        val imageView = helper.getView<ImageView>(R.id.image)
        Glide.with(context!!).load(item?.url).into(imageView)
    }
}