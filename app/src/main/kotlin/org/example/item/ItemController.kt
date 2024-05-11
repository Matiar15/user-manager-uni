package org.example.item

import jakarta.validation.Valid
import org.example.validation.group.Patch
import org.example.validation.group.Post
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class ItemController(
    private val itemService: ItemService
) {
    @PostMapping("/items")
    @ResponseStatus(CREATED)
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
    @ResponseStatus(NO_CONTENT)
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

    @GetMapping("/items")
    fun fetchByFilter(
        @Valid filter: ItemFilterRequest,
        page: Pageable
    ) = itemService.fetchByFilter(filter.asFilter(), page).map { it.toResponse() }

    @DeleteMapping("/items/{id}")
    @ResponseStatus(NO_CONTENT)
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