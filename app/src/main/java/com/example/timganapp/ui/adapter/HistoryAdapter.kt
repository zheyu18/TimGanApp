package com.example.timganapp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.timganapp.R
import com.example.timganapp.repository.History

class HistoryAdapter(layoutId: Int, data: List<History>) :
    BaseQuickAdapter<History, BaseViewHolder>(layoutId, data) {
    override fun convert(helper: BaseViewHolder?, item: History?) {
        helper?.setText(R.id.content, "${item?.content}(${item?.date})")
    }
}