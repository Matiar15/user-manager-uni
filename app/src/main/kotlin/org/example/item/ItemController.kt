package org.example.item

import org.example.validation.group.Patch
import org.example.validation.group.Post
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class ItemController(
    private val itemService: ItemService
) {
    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @Validated(Post::class) @RequestBody request: ItemRequest
    ): Unit = with(request) {
        itemService.createItem(
            name!!,
            category!!.id!!,
            auction!!.id!!,
            producedAt!!,
            quality!!,
            price!!
        )
    }

    @PatchMapping("/items/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun patch(
        @PathVariable id: Int,
        @Validated(Patch::class) @RequestBody request: ItemRequest
    ): Unit = with(request) {
        itemService.patchItem(
            id,
            name,
            category?.id,
            auction?.id,
            producedAt,
            quality,
            price
        )
    }

    @GetMapping("/items/{id}")
    fun findById(@PathVariable id: Int) = itemService.findById(id).toResponse()

    @GetMapping("/auctions/{auctionId}/items")
    fun findByAuction(@PathVariable auctionId: Int) = itemService.findByAuctionId(auctionId).toResponse()

    @DeleteMapping("/items/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
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