import com.example.timganapp.common.Type
import com.example.timganapp.ui.fragment.epg.ArticleFragment

class ExpendFragmet  : ArticleFragment(){

    companion object {
        fun newInstance():ExpendFragmet {
            return ExpendFragmet()
        }
    }

    override fun getType(): String {
        return Type.拓展资源.name
    }
}