package org.example.item

import org.example.auction.AuctionRepository
import org.example.category.CategoryRepository
import org.example.category.requireById
import org.example.exception.ItemNotAuctionedException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

interface ItemService {
    fun createItem(
        name: String,
        categoryId: Int,
        auctionId: Int,
        price: Double
    ): Item

    fun patchItem(
        id: Int,
        name: String?,
        categoryId: Int?,
        price: Double?
    ): Item

    fun deleteItem(id: Int)

    fun findById(id: Int): Item

    fun findByAuctionId(auctionId: Int): Item

    fun fetchByFilter(filter: ItemFilter, page: Pageable): Page<Item>
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
        price: Double
    ): Item = itemRepository.save(
        Item().apply {
            this.name = name
            this.category = categoryRepository.requireById(categoryId)
            this.auction = auctionRepository.requireById(auctionId)
            this.price = price
        }
    )

    override fun patchItem(
        id: Int,
        name: String?,
        categoryId: Int?,
        price: Double?
    ): Item {
        val item = itemRepository.requireById(id)
        name?.let { item.name = it }
        categoryId?.let { item.category = categoryRepository.requireById(it) }
        price?.let { item.price = price }
        return itemRepository.save(item)
    }

    override fun deleteItem(id: Int) {
        val item = itemRepository.requireById(id)
        itemRepository.delete(item)
    }

    override fun findById(id: Int): Item = itemRepository.requireById(id)

    override fun findByAuctionId(auctionId: Int): Item =
        itemRepository.findByAuctionId(auctionId) ?: throw ItemNotAuctionedException(auctionId)

    override fun fetchByFilter(filter: ItemFilter, page: Pageable): Page<Item> {
        return itemRepository.fetchByFilter(filter, page)
    }
}
