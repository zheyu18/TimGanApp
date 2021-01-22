package com.example.timganapp.ui.fragment.epg
import com.example.timganapp.common.Type


class IOSFragmet  : ArticleFragment(){

    companion object {
        fun newInstance():IOSFragmet {
            return IOSFragmet()
        }
    }

    override fun getType(): String {
        return Type.iOS.name
    }
}