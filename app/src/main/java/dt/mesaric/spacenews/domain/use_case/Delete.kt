package dt.mesaric.spacenews.domain.use_case

import dt.mesaric.spacenews.domain.model.Article
import dt.mesaric.spacenews.domain.repository.ArticleRepository

class Delete (
    private val repository: ArticleRepository
    ){
    suspend operator fun invoke(article: Article){
        repository.delete(article)
    }
}