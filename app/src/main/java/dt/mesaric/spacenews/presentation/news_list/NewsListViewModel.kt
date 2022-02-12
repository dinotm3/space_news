package dt.mesaric.spacenews.presentation.news_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dt.mesaric.spacenews.common.Constants.ERROR_UNEXPECTED
import dt.mesaric.spacenews.common.Resource
import dt.mesaric.spacenews.data.repository.ArticleRepositoryImpl
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val getArticles: ArticleRepositoryImpl,
    private val delete: ArticleRepositoryImpl

): ViewModel(){

    private val _state = mutableStateOf(NewsListState())
    val state: State<NewsListState> = _state

    init {
        getNewsArticles()
    }

    fun onEvent(event: NewsEvent){
        when(event){
            is NewsEvent.Delete -> {
                viewModelScope.launch {
                   delete.delete(event.article)
                }
            }
        }
    }

    private fun getNewsArticles(){
        getArticles().onEach { result ->
            when(result){
                is Resource.Success -> {
                    _state.value = NewsListState(
                        newsArticles = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = NewsListState(
                        error = result.message ?: ERROR_UNEXPECTED
                    )
                }
                is Resource.Loading -> {
                    _state.value = NewsListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}