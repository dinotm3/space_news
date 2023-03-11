package dt.mesaric.spacenews.presentation.news_list.components.news_list_item


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import dt.mesaric.spacenews.domain.model.Article
import dt.mesaric.spacenews.presentation.ui.theme.LocalSpacing
import java.io.File

private val IMAGE_MODIFIER = 90.dp
@ExperimentalCoilApi
@Composable
fun ArticleListItem(
    article: Article,
    onItemClick: (Article) -> Unit
) {

    Card(
        modifier = Modifier
            .padding(
                horizontal = LocalSpacing.current.medium,
                vertical = LocalSpacing.current.small
            )
            .fillMaxWidth(),
        elevation = LocalSpacing.current.mini,
        backgroundColor = Color.DarkGray,
        shape = RoundedCornerShape(corner = CornerSize(LocalSpacing.current.large))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClick(article) }
                .padding(LocalSpacing.current.small),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            //val painter = rememberImagePainter(data = File(article.picturePath!!))
            Image(
                // painter = painter
                painter = painterResource(id = dt.mesaric.spacenews.R.drawable.ic_rocket),
                contentDescription = null,
                modifier = Modifier.size(IMAGE_MODIFIER)
            )
            Text(
                text = "${article.title}. \n\nDate: ${article.publishedDateTime}",
                style = MaterialTheme.typography.body2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}