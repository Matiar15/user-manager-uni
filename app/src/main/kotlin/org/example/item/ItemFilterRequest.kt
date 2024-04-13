package org.example.item

import org.example.auction.AuctionFilterRequest
import org.example.util.RangeDto

data class ItemFilterRequest(
    val name: Collection<String>? = null,
    val category: Collection<RefCategory>? = null,
    val auction: AuctionFilterRequest? = null,
    val producedAt: RangeDto.DateRangeDto? = null,
    val quality: Collection<Item.Quality>? = null,
    val price: Collection<Double>? = null
) {
    fun asFilter(): ItemFilter {
        return ItemFilter(
            name,
            category?.mapNotNull { it.id },
            auction?.asFilter(),
            producedAt?.asRange(),
            quality,
            price
        )
    }
}
