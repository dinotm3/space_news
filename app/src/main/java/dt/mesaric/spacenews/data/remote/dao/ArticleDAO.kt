package dt.mesaric.spacenews.data.remote.dao

import androidx.room.*
import dt.mesaric.spacenews.domain.model.Article


@Dao
interface ArticleDAO {

    @Query("select * from articles")
    suspend fun getArticles() : List<Article>

    @Query("select * from articles where id = :id")
    suspend fun getArticleById(id: Int): Article?

    @Insert
    fun insert(articles: List<Article>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(article: Article)

    @Update
    fun update(article: Article)

    @Delete
    suspend fun delete(article: Article)
}