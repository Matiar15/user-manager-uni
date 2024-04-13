package org.example.item

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/items")
class ItemController(
    private val itemService: ItemService
) {
    @PostMapping
    fun create(@Validated @RequestBody request: ItemRequest) =
        with(request) {
            itemService.createItem(
                name!!,
                category!!.id!!,
                auction!!.id!!,
                producedAt!!,
                quality!!,
                price!!
            )
        }.toResponse()

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): Unit = itemService.deleteItem(id)

    private fun Item.toResponse() {
        ItemResponse(
            name,
            RefCategory(category.id),
            RefAuction(auction.id),
            producedAt!!,
            quality!!,
            price!!
        )
    }
}