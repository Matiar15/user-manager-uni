package org.example.item

import java.time.LocalDate

data class ItemResponse(
    val name: String,
    val category: RefCategory,
    val auction: RefAuction,
    val producedAt: LocalDate,
    val quality: Item.Quality,
    val price: Double
)
