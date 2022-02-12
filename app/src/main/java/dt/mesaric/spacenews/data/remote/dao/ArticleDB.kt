package dt.mesaric.spacenews.data.remote.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dagger.Binds
import dagger.Provides
import dt.mesaric.spacenews.domain.model.Article

@Database(entities = [Article::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class ArticleDB : RoomDatabase() {
    abstract fun articleDAO() : ArticleDAO
    companion object {
        @Volatile private var INSTANCE: ArticleDB? = null // Volatile makes threads use the same singleton, no local copies
        fun getInstance(context: Context): ArticleDB =
            // 1st thread has entered but was stopped to let the 2nd thread into
            // 2nd thread comes here
            INSTANCE ?:
            //2nd thread stops
            //1st thread enters
            synchronized(ArticleDB::class.java) { // no 2 threads can simultaneously enter the critical section
                // double checked locking
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private const val DATABASE_NAME = "articles.db"
        private fun buildDatabase(context: Context)
        = Room.databaseBuilder(
            context.applicationContext,
            ArticleDB::class.java,
            DATABASE_NAME)
            .build()
    }
}