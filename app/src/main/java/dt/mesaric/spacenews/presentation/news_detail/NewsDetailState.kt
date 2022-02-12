package dt.mesaric.spacenews.presentation.news_detail

import dt.mesaric.spacenews.domain.model.Article


data class NewsDetailState(
    val isLoading: Boolean = false,
    val article: Article? = null,
    val error: String = ""
)
