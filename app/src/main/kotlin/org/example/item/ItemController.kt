package org.example.item

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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