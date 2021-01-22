package com.example.timganapp.ui.fragment.epg
import com.example.timganapp.common.Type

class VideoFragmet  : ArticleFragment(){

    companion object {
        fun newInstance():VideoFragmet {
            return VideoFragmet()
        }
    }

    override fun getType(): String {
        return Type.休息视频.name
    }
}