package dt.mesaric.spacenews.domain.repository

import dt.mesaric.spacenews.common.Resource
import dt.mesaric.spacenews.domain.model.Article

import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    suspend fun getArticles(): Flow<Resource<List<Article>>>
    suspend fun getArticleById(articleId: Int): Flow<Resource<Article>>
    suspend fun delete(article: Article)
}