package org.example.auction

import org.example.Auction
import org.example.EntityMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import java.time.LocalDateTime

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
class AuctionControllerTest extends Specification {
    @Autowired
    MockMvc mvc

    @Autowired
    AuctionService auctionService

    @Autowired
    EntityMapper entityMapper

    private final String ENDPOINT = "/api/auctions"

    def "create auction all good"() {
        given:
        def name = "TEEEEEEEEEEEEEEEST"
        def startsAt = "2003-03-03T03:03"
        def description = "TEEEEEEEEEEEEEEEST"
        def price = 12.36

        def postContent = """
        |{
        |   "name": "$name",
        |   "startsAt": "$startsAt",
        |   "description": "$description",
        |   "price": $price 
        |}""".stripMargin()


        def auction = new Auction().tap {
            it.setId(2)
            it.setActive(true)
            it.setEndsAt(LocalDateTime.of(2003, 3, 3, 3, 3).plusHours(3))
            it.setDescription("TEST1")
            it.setPrice(2.0)
            it.setName("TEST")
            it.setStartsAt(LocalDateTime.of(2003, 3, 3, 3, 3))
        }

        when:
        def result = mvc.perform(MockMvcRequestBuilders.post(ENDPOINT)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(postContent)
        )

        then:
        result.andExpect { status().isOk() }

        and:
        1 * auctionService.createAuction(
                name,
                LocalDateTime.of(2003, 3, 3, 3, 3),
                description,
                price
        ) >> auction

        and:
        1 * entityMapper.fromAuction(auction) >> new AuctionResponse(
                auction.id,
                auction.name,
                auction.startsAt,
                auction.endsAt,
                auction.description,
                auction.price
        )
    }


    @TestConfiguration
    @Configuration
    static class TestConfig {
        def factory = new DetachedMockFactory()

        @Bean
        AuctionService auctionService() {
            factory.Mock(AuctionService)
        }

        @Bean
        EntityMapper entityMapper() {
            factory.Mock(EntityMapper)
        }
    }
}
