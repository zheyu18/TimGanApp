package com.example.timganapp.ui.fragment.epg
import com.example.timganapp.common.Type

class WebFragmet : ArticleFragment(){

    companion object {
        fun newInstance():WebFragmet {
            return WebFragmet()
        }
    }

    override fun getType(): String {
        return Type.前端.name
    }
}