package dt.mesaric.spacenews.presentation.screen

private const val listRoute = "news_list_screen"
private const val listTitle = "news_list"
private const val detailRoute = "news_detail_screen"
private const val detailTitle = "news_detail"

sealed class NewsScreen(val route: String, val title: String) {
    object NewsListScreen: NewsScreen(listRoute, listTitle)
    object NewsDetailScreen: NewsScreen(detailRoute, detailTitle)
}

