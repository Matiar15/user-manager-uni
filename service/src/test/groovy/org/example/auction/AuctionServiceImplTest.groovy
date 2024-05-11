package org.example.auction

import org.example.Auction
import org.example.exception.AuctionAlreadyEndedException
import org.example.exception.AuctionAlreadyStartedException
import org.example.exception.AuctionNotStartedException
import org.example.exception.AuctionPriceException
import org.example.exception.StartsAtExpiredException
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
        underTest.createAuction(name, startsAt, description, price, 1)

        then:
        1 * auctionRepository.save({ Auction a ->
            a.active == true
            a.endsAt == startsAt.plusHours(3)
            a.startsAt == startsAt
            a.startPrice == price
            a.name == name
            a.description == description
            a.ownerId == 1
        }) >> _

        and:
        0 * _
    }

    def "should patch auction all good with provided parameters"() {
        given:
        def dateTime = LocalDateTime.now().plusDays(1)
        def auction = new Auction(
                id: 1,
                name: "asd",
                description: "test",
                startPrice: 20.36D,
                startsAt: dateTime,
                endsAt: dateTime.plusHours(3)
        )

        def newName = "teeest"
        def newDescription = "teeeeeest"
        def newPrice = 200.36D
        def newStartsAt = LocalDateTime.now().plusDays(2)

        when:
        def result = underTest.patchAuction(1, newName, newDescription, newPrice, newStartsAt)

        then:
        1 * auctionRepository.requireById(1) >> auction

        and:
        result.name == newName
        result.description == newDescription
        result.startPrice == newPrice
        result.startsAt == newStartsAt
        result.endsAt == newStartsAt.plusHours(3)

        and:
        0 * _
    }


    def "should patch auction all good with provided parameters"() {
        given:
        def dateTime = LocalDateTime.now().plusDays(1)
        def auction = new Auction(
                id: 1,
                name: "asd",
                description: "test",
                startPrice: 20.36D,
                startsAt: dateTime,
                endsAt: dateTime.plusHours(3)
        )

        def newName = "teeest"
        def newDescription = "teeeeeest"
        def newPrice = 200.36D
        def newStartsAt = LocalDateTime.now().plusDays(2)

        when:
        def result = underTest.patchAuction(1, newName, newDescription, newPrice, newStartsAt)

        then:
        1 * auctionRepository.requireById(1) >> auction

        and:
        result.name == newName
        result.description == newDescription
        result.startPrice == newPrice
        result.startsAt == newStartsAt
        result.endsAt == newStartsAt.plusHours(3)

        and:
        0 * _
    }

    def "should patch auction with already started auction"() {
        given:
        def dateTime = LocalDateTime.now().plusDays(1)
        def auction = new Auction(
                id: 1,
                name: "asd",
                description: "test",
                startPrice: 20.36D,
                startsAt: dateTime,
                endsAt: dateTime.plusHours(3)
        )

        def newName = "teeest"
        def newDescription = "teeeeeest"
        def newPrice = 200.36D
        def newStartsAt = LocalDateTime.now()

        when:
        underTest.patchAuction(1, newName, newDescription, newPrice, newStartsAt)

        then:
        1 * auctionRepository.requireById(1) >> auction

        and:
        thrown StartsAtExpiredException
        0 * _
    }

    def "should delete by id all good"() {
        given:
        def auction = new Auction(
                id: 1,
                name: "asd",
                description: "test",
                startPrice: 20.36D,
                startsAt: LocalDateTime.now().plusDays(1),
                endsAt: LocalDateTime.now().plusDays(1).plusHours(3)
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


    def "should delete by id with already started auction"() {
        given:
        def auction = new Auction(
                id: 1,
                name: "asd",
                description: "test",
                startPrice: 20.36D,
                startsAt: LocalDateTime.of(2023, 3, 3, 3, 3),
                endsAt: LocalDateTime.of(2023, 3, 3, 6, 3)
        )

        when:
        underTest.deleteById(auction.id)

        then:
        1 * auctionRepository.requireById(auction.id) >> auction

        and:
        thrown AuctionAlreadyStartedException
        0 * _
    }

    def "should bid on auction all good"() {
        given:
        def dateTime = LocalDateTime.now().minusHours(1)
        def auction = new Auction(
                id: 1,
                name: "asd",
                description: "test",
                startPrice: 20.36D,
                currentPrice: 19.36D,
                startsAt: dateTime,
                endsAt: dateTime.plusHours(3)
        )
        def auctionId = 1
        def winnerId = 123
        def winnerEmail = "test@test.com"
        def newPrice = 20.0D

        when:
        underTest.bidOnAuction(auctionId, winnerId, winnerEmail, newPrice)

        then:
        1 * auctionRepository.requireById(auctionId) >> auction

        and:
        1 * auctionRepository.save({ Auction a ->
            a.currentPrice == 20.0D
            a.winnersEmail == "test@test.com"
            a.winnersId == winnerId
        })

        and:
        0 * _
    }


    def "should bid on auction with auction price exception"() {
        given:
        def dateTime = LocalDateTime.now().minusHours(1)
        def auction = new Auction(
                id: 1,
                name: "asd",
                description: "test",
                startPrice: 20.36D,
                currentPrice: 20.36D,
                startsAt: dateTime,
                endsAt: dateTime.plusHours(3)
        )
        def auctionId = 1
        def winnerId = 123
        def winnerEmail = "test@test.com"
        def newPrice = 20.0D

        when:
        underTest.bidOnAuction(auctionId, winnerId, winnerEmail, newPrice)

        then:
        1 * auctionRepository.requireById(auctionId) >> auction

        and:
        thrown AuctionPriceException
        0 * _
    }


    def "should bid on auction with thrown auction not started exception"() {
        given:
        def dateTime = LocalDateTime.now().plusDays(1)
        def auction = new Auction(
                id: 1,
                name: "asd",
                description: "test",
                startPrice: 20.36D,
                currentPrice: 20.36D,
                startsAt: dateTime,
                endsAt: dateTime.plusHours(3)
        )
        def auctionId = 1
        def winnerId = 123
        def winnerEmail = "test@test.com"
        def newPrice = 30.0D

        when:
        underTest.bidOnAuction(auctionId, winnerId, winnerEmail, newPrice)

        then:
        1 * auctionRepository.requireById(auctionId) >> auction

        and:
        thrown AuctionNotStartedException
        0 * _
    }


    def "should bid on auction already ended exception"() {
        given:
        def dateTime = LocalDateTime.now().minusDays(1)
        def auction = new Auction(
                id: 1,
                name: "asd",
                description: "test",
                startPrice: 20.36D,
                currentPrice: 20.36D,
                startsAt: dateTime,
                endsAt: dateTime.plusHours(3)
        )
        def auctionId = 1
        def winnerId = 123
        def winnerEmail = "test@test.com"
        def newPrice = 30.0D

        when:
        underTest.bidOnAuction(auctionId, winnerId, winnerEmail, newPrice)

        then:
        1 * auctionRepository.requireById(auctionId) >> auction

        and:
        thrown AuctionAlreadyEndedException
        0 * _
    }
}
