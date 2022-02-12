package dt.mesaric.spacenews
import android.app.Application
import dagger.Provides
import dagger.hilt.android.HiltAndroidApp
import dt.mesaric.spacenews.data.remote.dao.ArticleDAO
import dt.mesaric.spacenews.data.remote.dao.ArticleDB
import javax.inject.Inject


@HiltAndroidApp
class SpaceNewsApplication : Application() {

    private lateinit var articleDAO: ArticleDAO

    override fun onCreate() {
        super.onCreate()
        var db = ArticleDB.getInstance(this)
        articleDAO = db.articleDAO()
    }

    fun getArticleDAO() = articleDAO
}