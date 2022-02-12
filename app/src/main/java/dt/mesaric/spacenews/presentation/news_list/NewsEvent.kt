package dt.mesaric.spacenews.presentation.news_list


import dt.mesaric.spacenews.domain.model.Article

sealed class NewsEvent {
    data class Delete(val article: Article): NewsEvent()
}
