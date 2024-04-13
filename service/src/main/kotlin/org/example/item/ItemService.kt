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

    fun patchItem(
        id: Int,
        name: String?,
        categoryId: Int?,
        auctionId: Int?,
        producedAt: LocalDate?,
        quality: Item.Quality?,
        price: Double?
    ): Item

    fun deleteItem(id: Int)
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

    override fun patchItem(
        id: Int,
        name: String?,
        categoryId: Int?,
        auctionId: Int?,
        producedAt: LocalDate?,
        quality: Item.Quality?,
        price: Double?
    ): Item {
        val item = itemRepository.requireById(id)
        name?.let { item.name = it }
        categoryId?.let { item.category = categoryRepository.requireById(it) }
        auctionId?.let { item.auction = auctionRepository.requireById(it) }
        producedAt?.let { item.producedAt = producedAt }
        quality?.let { item.quality = quality }
        price?.let { item.price = price }
        return item
    }

    override fun deleteItem(id: Int) {
        val item = itemRepository.requireById(id)
        itemRepository.delete(item)
    }
}