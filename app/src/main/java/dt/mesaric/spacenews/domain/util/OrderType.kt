package dt.mesaric.spacenews.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}