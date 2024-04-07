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
}
