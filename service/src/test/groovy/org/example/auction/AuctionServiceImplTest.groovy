package org.example.auction

import org.example.Auction
import spock.lang.Specification

import java.time.LocalDateTime

class AuctionServiceImplTest extends Specification {
    AuctionRepository auctionRepository = Mock()

    def underTest = new AuctionServiceImpl(auctionRepository)

    def "should create auction all good"() {
        given:
        def name = "asd"
        def startsAt = LocalDateTime.of(2023, 3, 3, 3, 3)
        def description = "asdasdasd"
        def price = 20.36D

        when:
        underTest.createAuction(name, startsAt, description, price)

        then:
        1 * auctionRepository.save({ Auction a ->
            a.active == true
            a.endsAt == startsAt.plusHours(3)
            a.startsAt == startsAt
            a.price == price
            a.name == name
            a.description == description
        }) >> _

        and:
        0 * _
    }

    def "should patch auction all good with provided parameters"() {
        given:
        def auction = new Auction(
                id: 1,
                name: "asd",
                description: "test",
                price: 20.36D,
                startsAt: LocalDateTime.of(2023, 3, 3, 3, 3),
                endsAt: LocalDateTime.of(2023, 3, 3, 6, 3)
        )

        def newName = "teeest"
        def newDescription = "teeeeeest"
        def newPrice = 200.36D
        def newStartsAt = LocalDateTime.of(2024, 4, 4, 4, 4)

        when:
        def result = underTest.patchAuction(1, newName, newDescription, newPrice, newStartsAt)

        then:
        1 * auctionRepository.requireById(1) >> auction

        and:
        result.name == newName
        result.description == newDescription
        result.price == newPrice
        result.startsAt == newStartsAt
        result.endsAt == newStartsAt.plusHours(3)

        and:
        0 * _
    }

    def "should delete by id all good"() {
        given:
        def auction = new Auction(
                id: 1,
                name: "asd",
                description: "test",
                price: 20.36D,
                startsAt: LocalDateTime.of(2023, 3, 3, 3, 3),
                endsAt: LocalDateTime.of(2023, 3, 3, 6, 3)
        )

        when:
        underTest.deleteById(auction.id)

        then:
        1 * auctionRepository.requireById(auction.id) >> auction

        and:
        1 * auctionRepository.delete(auction)

        and:
        0 * _
    }
}
