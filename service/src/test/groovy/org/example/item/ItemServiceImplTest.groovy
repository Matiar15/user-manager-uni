package org.example.item

import org.example.Auction
import org.example.auction.AuctionRepository
import org.example.category.Category
import org.example.category.CategoryRepository
import org.example.exception.ItemNotAuctionedException
import spock.lang.Specification

import java.time.LocalDate

class ItemServiceImplTest extends Specification {
    ItemRepository itemRepository = Mock()
    CategoryRepository categoryRepository = Mock()
    AuctionRepository auctionRepository = Mock()

    def underTest = new ItemServiceImpl(
            itemRepository,
            categoryRepository,
            auctionRepository
    )

    def "should create item all good"() {
        given:
        def name = "tesuasdaadst"
        def categoryId = 1
        def auctionId = 2
        def producedAt = LocalDate.of(2023, 3, 3)
        def quality = Item.Quality.FACTORY_NEW
        def price = -10.36

        when:
        underTest.createItem(name, categoryId, auctionId, producedAt, quality, price)

        then:
        1 * categoryRepository.findById(categoryId) >> Optional.of(new Category(id: categoryId))

        and:
        1 * auctionRepository.requireById(auctionId) >> new Auction(id: auctionId)

        and:
        1 * itemRepository.save({ Item i ->
            i.name == name
            i.category.id == categoryId
            i.auction.id == auctionId
            i.producedAt == producedAt
            i.quality == quality
            i.price == price
        }) >> _

        and:
        0 * _
    }

    def "should patch item all good"() {
        given:
        def name = "tesuasdaadst"
        def categoryId = 1
        def auctionId = 2
        def producedAt = LocalDate.of(2023, 3, 3)
        def quality = Item.Quality.FACTORY_NEW
        def price = -10.36
        def id = 1

        when:
        def result = underTest.patchItem(id, name, categoryId, auctionId, producedAt, quality, price)

        then:
        1 * itemRepository.findById(1) >> Optional.of(new Item(id: 1))

        and:
        1 * categoryRepository.findById(categoryId) >> Optional.of(new Category(id: categoryId))

        and:
        1 * auctionRepository.requireById(auctionId) >> new Auction(id: auctionId)

        and:
        result.name == name
        result.category.id == categoryId
        result.auction.id == auctionId
        result.producedAt == producedAt
        result.quality == quality
        result.price == price
    }

    def "should find item by auction with thrown exception"() {
        given:
        def auctionId = 2

        when:
        underTest.findByAuctionId(auctionId)

        then:
        1 * itemRepository.findByAuctionId(auctionId) >> null

        and:
        thrown ItemNotAuctionedException
        0 * _
    }

    def "should find item by auction all good"() {
        given:
        def auctionId = 2

        when:
        underTest.findByAuctionId(auctionId)

        then:
        1 * itemRepository.findByAuctionId(auctionId) >> new Item(id: 1, auction: new Auction(id: auctionId))

        and:
        0 * _
    }
}
