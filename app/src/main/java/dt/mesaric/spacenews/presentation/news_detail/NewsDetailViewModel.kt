package dt.mesaric.spacenews.presentation.news_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dt.mesaric.spacenews.common.Constants
import dt.mesaric.spacenews.common.Constants.ERROR_UNEXPECTED
import dt.mesaric.spacenews.common.Resource
import dt.mesaric.spacenews.data.repository.ArticleRepositoryImpl
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val getArticle: ArticleRepositoryImpl,
    savedStateHandle: SavedStateHandle
): ViewModel(){

    private val _state = mutableStateOf(NewsDetailState())
    val state: State<NewsDetailState> = _state

    init {
        savedStateHandle.get<String>(Constants.PARAM_NEWS_ID_)?.toInt()?.let {
                articleId -> getArticleById(articleId)
        }
    }

    private fun getArticleById(articleId: Int){
        getArticle(articleId).onEach { result ->
            when(result){
                is Resource.Success -> {
                    _state.value = NewsDetailState(article = result.data)
                }
                is Resource.Error -> {
                    _state.value = NewsDetailState(
                        error = result.message ?: ERROR_UNEXPECTED
                    )
                }
                is Resource.Loading -> {
                    _state.value = NewsDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}