package org.example.item

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate
import org.example.QAuction
import org.example.RangePredicate
import org.example.auction.AuctionRepository
import org.example.category.QCategory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.data.support.PageableExecutionUtils

interface ItemCustomRepository {
    fun findByAuctionId(auctionId: Int): Item?

    fun fetchByFilter(filter: ItemFilter, page: Pageable): Page<Item>

    fun asPredicate(root: QItem, category: QCategory, auction: QAuction, filter: ItemFilter?): Predicate?
}

class ItemCustomRepositoryImpl(
    private val auctionRepository: AuctionRepository
) : QuerydslRepositorySupport(Item::class.java), ItemCustomRepository {
    override fun findByAuctionId(auctionId: Int): Item? {
        val root = QItem.item
        return from(root)
            .where(root.auction.id.eq(auctionId))
            .fetchFirst()
    }

    override fun fetchByFilter(filter: ItemFilter, page: Pageable): Page<Item> {
        val root = QItem.item
        val auction = QAuction.auction
        val category = QCategory.category

        var query = from(root)
            .join(root.auction, auction).fetchJoin()
            .join(root.category, category).fetchJoin()
            .where(asPredicate(root, category, auction, filter))

        query = querydsl!!.applyPagination(page, query)

        return PageableExecutionUtils.getPage(query.fetch(), page, query::fetchCount)
    }

    override fun asPredicate(root: QItem, category: QCategory, auction: QAuction, filter: ItemFilter?): Predicate? {
        if (filter == null) return null
        return BooleanBuilder()
            .and(
                with(filter) {
                    name?.let { root.name.`in`(name) }
                    this.category?.let { root.category.id.`in`(it) }
                    this.auction?.let { auctionRepository.asPredicate(auction, it) }
                    producedAt?.let { RangePredicate.matches(it, root.producedAt) }
                    quality?.let { root.quality.`in`(it) }
                    price?.let { root.price.`in`(it) }
                }
            )
    }

}
