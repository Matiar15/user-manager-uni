package org.example.item

import org.example.auction.AuctionFilter

data class ItemFilter(
    val name: Collection<String>?,
    val category: Collection<Int>?,
    val auction: AuctionFilter?,
    val price: Collection<Double>?
)
