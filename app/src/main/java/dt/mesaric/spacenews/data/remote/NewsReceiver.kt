package dt.mesaric.spacenews.data.remote

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dt.mesaric.spacenews.DATA_IMPORTED
import dt.mesaric.spacenews.MainActivity
import dt.mesaric.spacenews.common.setBooleanPreference
import dt.mesaric.spacenews.common.startActivity

class NewsReceiver : BroadcastReceiver() { // this guy is not a context

    override fun onReceive(context: Context, intent: Intent) {
        context.setBooleanPreference(DATA_IMPORTED, true) // data is in the db
        context.startActivity<MainActivity>()
    }
}