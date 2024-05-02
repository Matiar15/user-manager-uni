package org.example.item

import com.google.common.collect.Range
import org.example.auction.AuctionFilter
import java.time.LocalDate

data class ItemFilter(
    val name: Collection<String>?,
    val category: Collection<Int>?,
    val auction: AuctionFilter?,
    val producedAt: Range<LocalDate>?,
    val quality: Collection<Item.Quality>?,
    val price: Collection<Double>?
)
