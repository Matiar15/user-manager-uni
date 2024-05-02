package org.example.item

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

interface ItemCustomRepository {
    fun findByAuctionId(auctionId: Int): Item?
}

class ItemCustomRepositoryImpl : QuerydslRepositorySupport(Item::class.java), ItemCustomRepository {
    override fun findByAuctionId(auctionId: Int): Item? {
        val root = QItem.item
        return from(root)
            .where(root.auction.id.eq(auctionId))
            .fetchFirst()
    }

}
