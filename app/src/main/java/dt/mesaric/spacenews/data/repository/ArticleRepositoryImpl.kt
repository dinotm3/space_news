package dt.mesaric.spacenews.data.repository

import dt.mesaric.spacenews.common.Constants.ERROR_SERVER
import dt.mesaric.spacenews.common.Constants.ERROR_UNEXPECTED
import dt.mesaric.spacenews.common.Resource
import dt.mesaric.spacenews.domain.model.Article
import dt.mesaric.spacenews.data.remote.dao.ArticleDAO
import dt.mesaric.spacenews.domain.repository.ArticleRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val dao: ArticleDAO
): ArticleRepository {
    @ExperimentalCoroutinesApi
    override suspend fun getArticles(): Flow<Resource<List<Article>>> {
        return callbackFlow { invoke() }
    }

    @ExperimentalCoroutinesApi
    override suspend fun getArticleById(articleId: Int): Flow<Resource<Article>> {
        return callbackFlow { invoke()  }
    }

    override suspend fun delete(article: Article){
        dao.delete(article)
    }

    operator fun invoke(): Flow<Resource<List<Article>>> = flow {
        try {
            emit(Resource.Loading())
            val articles = dao.getArticles()
            emit(Resource.Success(articles))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: ERROR_UNEXPECTED))
        } catch (e: IOException) {
            emit(Resource.Error(ERROR_SERVER))
        }
    }

    operator fun invoke(articleId: Int): Flow<Resource<Article>> = flow {
        try {
            emit(Resource.Loading<Article>())
            val article = dao.getArticleById(articleId)
            emit(Resource.Success(article))
        } catch (e: HttpException) {
            emit(Resource.Error<Article>(e.localizedMessage ?: ERROR_UNEXPECTED))
        } catch(e: IOException) {
            emit(Resource.Error<Article>(ERROR_SERVER))
        }
    } as Flow<Resource<Article>>
}