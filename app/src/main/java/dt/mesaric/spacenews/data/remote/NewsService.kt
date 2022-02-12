package dt.mesaric.spacenews.data.remote

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import dt.mesaric.spacenews.SpaceNewsApplication
import dt.mesaric.spacenews.common.sendBroadcast
import dt.mesaric.spacenews.data.parser.parse


private const val JOB_ID = 1

class NewsService : JobIntentService() { // this is also context, broadcast receiver is not
    override fun onHandleWork(intent: Intent) {
        val articles = parse(this)
        val dao = (applicationContext as SpaceNewsApplication).getArticleDAO()
        dao.insert(articles)
        sendBroadcast<NewsReceiver>()
    }

    companion object {
        fun enqueueWork(context: Context, intent: Intent)
        = enqueueWork(context, NewsService::class.java, JOB_ID, intent)
    }
}