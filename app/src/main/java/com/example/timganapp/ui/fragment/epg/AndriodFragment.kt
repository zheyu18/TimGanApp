package com.example.timganapp.ui.fragment.epg

import com.example.timganapp.common.Type

class AndriodFragment :ArticleFragment(){
    companion object {
        fun newInstance():AndriodFragment {
            return AndriodFragment()
        }
    }

    override fun getType(): String {
        return Type.Andriod.name
    }

}