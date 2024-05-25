package org.example.item

import org.example.auction.AuctionFilterRequest

data class ItemFilterRequest(
    val name: Collection<String>? = null,
    val category: Collection<RefCategory>? = null,
    val auction: AuctionFilterRequest? = null,
    val price: Collection<Double>? = null
) {
    fun asFilter(): ItemFilter {
        return ItemFilter(
            name,
            category?.mapNotNull { it.id },
            auction?.asFilter(null),
            price
        )
    }
}
