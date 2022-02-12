package dt.mesaric.spacenews.presentation.news_list

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import dt.mesaric.spacenews.presentation.news_list.components.news_list_item.ArticleListItem
import dt.mesaric.spacenews.presentation.screen.NewsScreen
import dt.mesaric.spacenews.presentation.ui.theme.LocalSpacing


private val ICON_SIZE: Dp = 100.dp
private const val ICON_DESC = "Icon"

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun NewsListScreen(
    navController: NavController,
    viewModel: NewsListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.newsArticles) { article ->
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        when (it){
                            DismissValue.DismissedToEnd -> {
                                //viewModel.onEvent(NewsEvent.Update(article))
                            }
                            DismissValue.DismissedToStart -> {
                                viewModel.onEvent(NewsEvent.Delete(article))
                            }
                            DismissValue.Default -> {

                            }
                        }
                        true
                    }
                )
                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
                    background = {
                                 val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
                                 val color by animateColorAsState(
                                     targetValue = when (dismissState.targetValue) {
                                         DismissValue.Default -> Color.Black
                                         DismissValue.DismissedToEnd -> Color.Green
                                         DismissValue.DismissedToStart -> Color.Red
                                     }
                                 )
                        val icon = when(direction) {
                            DismissDirection.StartToEnd -> Icons.Default.Done
                            DismissDirection.EndToStart -> Icons.Default.Delete
                        }
                        
                        val scale by animateFloatAsState(
                            targetValue = if (dismissState.targetValue == DismissValue.Default) 0.8f else 1.2f
                        )
                        val alignment = when(direction){
                            DismissDirection.EndToStart -> Alignment.CenterEnd
                            DismissDirection.StartToEnd -> Alignment.CenterStart
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(start = LocalSpacing.current.large, end = LocalSpacing.current.large),
                            contentAlignment = alignment
                        ) {
                            Icon(icon, contentDescription = ICON_DESC, modifier = Modifier.scale(scale).size(ICON_SIZE))
                        }
                    },
                    dismissContent = {
                        ArticleListItem(
                        article = article,
                        onItemClick = {
                            navController.navigate(
                                NewsScreen.NewsDetailScreen.route + "/${article.id}"
                            )
                        }
                    )}
                )
            }
        }
        if(state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = LocalSpacing.current.medium)
                    .align(Alignment.Center)
            )
        }
        if(state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}