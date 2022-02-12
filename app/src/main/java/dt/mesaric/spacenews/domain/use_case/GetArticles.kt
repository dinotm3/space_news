package dt.mesaric.spacenews.domain.use_case

import dt.mesaric.spacenews.domain.repository.ArticleRepository
import javax.inject.Inject

class GetArticles @Inject constructor(
    private val articleRepository: ArticleRepository
) {

}