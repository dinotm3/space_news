package dt.mesaric.spacenews.di

import android.app.Application
import android.content.Context
import androidx.annotation.NonNull
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dt.mesaric.spacenews.SpaceNewsApplication

import dt.mesaric.spacenews.common.Constants

import dt.mesaric.spacenews.data.remote.dao.ArticleDAO
import dt.mesaric.spacenews.data.remote.dao.ArticleDB
import dt.mesaric.spacenews.data.repository.ArticleRepositoryImpl


import dt.mesaric.spacenews.domain.repository.ArticleRepository


import retrofit2.Retrofit

import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideArticleDB(app: Application): ArticleDB {
        return ArticleDB.getInstance(app.applicationContext)
    }

    @Provides
    @Singleton
    fun provideArticleDAO(db: ArticleDB): ArticleDAO{
        return db.articleDAO()
    }

    @Provides
    @Singleton
    fun provideArticleRepository(dao: ArticleDAO): ArticleRepository {
        return ArticleRepositoryImpl(dao)
    }
}