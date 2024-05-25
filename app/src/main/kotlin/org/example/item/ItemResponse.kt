package org.example.item

data class ItemResponse(
    val id: Int,
    val name: String,
    val category: RefCategory,
    val auction: RefAuction,
    val price: Double
) {
    data class RefCategory(
        val id: Int,
        val name: String
    )
}
