package org.example.util

import org.example.Auction
import org.example.auction.AuctionResponse
import spock.lang.Specification

import java.time.LocalDateTime

class EntityMapperTest extends Specification {
    def underTest = new EntityMapper()
    def "should properly translate auction to auction response"() {
        given:
        def startsAt = LocalDateTime.of(2023, 3, 3, 3, 3)
        def auction = new Auction().tap {
            it.setId(2)
            it.setActive(true)
            it.setEndsAt(startsAt.plusHours(3))
            it.setDescription("TEST1")
            it.setStartPrice(2.0)
            it.setName("TEST")
            it.setStartsAt(startsAt)
            it.setOwnerId(1)
            it.setWinnerEmail("asd@test.com")
            it.setCurrentPrice(2.0)
        }

        when:
        def result = underTest.fromAuction(auction)

        then:
        result == new AuctionResponse(
                auction.id,
                auction.name,
                auction.startsAt,
                auction.endsAt,
                auction.description,
                auction.startPrice,
                auction.currentPrice,
                auction.winnerEmail,
                auction.winnerId,
                auction.ownerId
        )
    }
}
