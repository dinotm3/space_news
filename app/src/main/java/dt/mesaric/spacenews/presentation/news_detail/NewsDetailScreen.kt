package dt.mesaric.spacenews.presentation.news_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import dt.mesaric.spacenews.R
import dt.mesaric.spacenews.presentation.ui.theme.LocalSpacing
import java.io.File

private val IMAGE_MODIFIER_DETAIL: Dp = 400.dp
@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
fun NewsDetailScreen(
    viewModel: NewsDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Box(modifier = Modifier.fillMaxSize()) {
        state.article?.let { article ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(LocalSpacing.current.large)
            ) {
                item {
                    Row {
                        Text(
                            text = article.title!!,
                            style = MaterialTheme.typography.h3
                        )

                    }
                    Spacer(modifier = Modifier.padding(LocalSpacing.current.small))
                    //val painter = rememberImagePainter(data = File(article.picturePath!!))
                    Row {
                        Image(
                            // painter = painter
                            painter = painterResource(id = R.drawable.ic_rocket),
                            contentDescription = null,
                            modifier = Modifier.size(IMAGE_MODIFIER_DETAIL)
                        )
                    }

                    Row {
                        Text(
                            text = article.description!!,
                            style = MaterialTheme.typography.body2
                        )

                    }
                }
            }
            if (state.error.isNotBlank()) {
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
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}