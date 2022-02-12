package dt.mesaric.spacenews.domain.util

sealed class ArticleOrder (val orderType: OrderType){
    class Title (orderType: OrderType): ArticleOrder(orderType)
    class Date (orderType: OrderType): ArticleOrder(orderType)
}