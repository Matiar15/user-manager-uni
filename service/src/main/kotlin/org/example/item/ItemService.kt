package org.example.item

import org.example.auction.AuctionRepository
import org.example.category.CategoryRepository
import org.example.category.requireById
import org.springframework.stereotype.Service
import java.time.LocalDate

interface ItemService {
    fun createItem(
        name: String,
        categoryId: Int,
        auctionId: Int,
        producedAt: LocalDate,
        quality: Item.Quality,
        price: Double
    ): Item
}

@Service
class ItemServiceImpl(
    private val itemRepository: ItemRepository,
    private val categoryRepository: CategoryRepository,
    private val auctionRepository: AuctionRepository,
) : ItemService {
    override fun createItem(
        name: String,
        categoryId: Int,
        auctionId: Int,
        producedAt: LocalDate,
        quality: Item.Quality,
        price: Double
    ): Item = itemRepository.save(
        Item().apply {
            this.name = name
            this.category = categoryRepository.requireById(categoryId)
            this.auction = auctionRepository.requireById(auctionId)
            this.producedAt = producedAt
            this.quality = quality
            this.price = price
        }
    )
}