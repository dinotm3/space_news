package dt.mesaric.spacenews.presentation.news_list

import dt.mesaric.spacenews.domain.model.Article

data class NewsListState(
    val isLoading: Boolean = false,
    val newsArticles: List<Article> = emptyList(),
    val error: String = ""
)
