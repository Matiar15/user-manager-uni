package org.example.auction

import com.google.common.collect.Range
import org.example.Auction
import org.example.util.EntityMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import java.time.LocalDateTime

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
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
        def result = mvc.perform(post(ENDPOINT)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(postContent)
        )

        then:
        result.andExpect { status().isCreated() }

        and:
        1 * auctionService.createAuction(
                name,
                LocalDateTime.of(2003, 3, 3, 3, 3),
                description,
                price
        ) >> auction
    }

    def "should validate name blank all good"() {
        given:
        def name = "                     "
        def startsAt = "2003-03-03T03:03"
        def description = "asdasdasdasdasdasdasdasdasdsak"
        def price = 12.36

        def postContent = """
        |{
        |   "name": "$name",
        |   "startsAt": "$startsAt",
        |   "description": "$description",
        |   "price": $price 
        |}""".stripMargin()

        when:
        def result = mvc.perform(post(ENDPOINT)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(postContent)
        )

        then:
        result.andExpect { status().isBadRequest() }

        and:
        result.andReturn().resolvedException.message.contains("NotBlank.auctionRequest.name")

        and:
        0 * auctionService.createAuction(*_)
    }

    def "should validate name null all good"() {
        given:
        def name = null
        def startsAt = "2003-03-03T03:03"
        def description = "asdasdasdasdasdasdasdasdasdsak"
        def price = 12.36

        def postContent = """
        |{
        |   "name": $name,
        |   "startsAt": "$startsAt",
        |   "description": "$description",
        |   "price": $price 
        |}""".stripMargin()

        when:
        def result = mvc.perform(post(ENDPOINT)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(postContent)
        )

        then:
        result.andExpect { status().isBadRequest() }

        and:
        result.andReturn().resolvedException.message.contains("NotBlank.auctionRequest.name")

        and:
        0 * auctionService.createAuction(*_)
    }

    def "should validate name size to small all good"() {
        given:
        def name = "not10char"
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

        when:
        def result = mvc.perform(post(ENDPOINT)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(postContent)
        )

        then:
        result.andExpect { status().isBadRequest() }

        and:
        result.andReturn().resolvedException.message.contains("Size.auctionRequest.name")

        and:
        0 * auctionService.createAuction(*_)
    }


    def "should validate name size too big all good"() {
        given:
        def name = "aabove60charabove60charabove60charabove60charbove60charabove60char"
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

        when:
        def result = mvc.perform(post(ENDPOINT)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(postContent)
        )

        then:
        result.andExpect { status().isBadRequest() }

        and:
        result.andReturn().resolvedException.message.contains("Size.auctionRequest.name")

        and:
        0 * auctionService.createAuction(*_)
    }

    def "should validate description blank all good"() {
        given:
        def name = "asdasdasdasdasdasdas"
        def startsAt = "2003-03-03T03:03"
        def description = "                                                                          "
        def price = 12.36

        def postContent = """
        |{
        |   "name": "$name",
        |   "startsAt": "$startsAt",
        |   "description": "$description",
        |   "price": $price 
        |}""".stripMargin()

        when:
        def result = mvc.perform(post(ENDPOINT)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(postContent)
        )

        then:
        result.andExpect { status().isBadRequest() }

        and:
        result.andReturn().resolvedException.message.contains("NotBlank.auctionRequest.description")

        and:
        0 * auctionService.createAuction(*_)
    }

    def "should validate description null all good"() {
        given:
        def name = "asdasdasd"
        def startsAt = "2003-03-03T03:03"
        def description = null
        def price = 12.36

        def postContent = """
        |{
        |   "name": "$name",
        |   "startsAt": "$startsAt",
        |   "description": $description,
        |   "price": $price 
        |}""".stripMargin()

        when:
        def result = mvc.perform(post(ENDPOINT)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(postContent)
        )

        then:
        result.andExpect { status().isBadRequest() }

        and:
        result.andReturn().resolvedException.message.contains("NotBlank.auctionRequest.description")

        and:
        0 * auctionService.createAuction(*_)
    }

    def "should validate description size too small all good"() {
        given:
        def name = "asdasdasdasdasdasdas"
        def startsAt = "2003-03-03T03:03"
        def description = "not10char"
        def price = 12.36

        def postContent = """
        |{
        |   "name": "$name",
        |   "startsAt": "$startsAt",
        |   "description": "$description",
        |   "price": $price 
        |}""".stripMargin()

        when:
        def result = mvc.perform(post(ENDPOINT)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(postContent)
        )

        then:
        result.andExpect { status().isBadRequest() }

        and:
        result.andReturn().resolvedException.message.contains("Size.auctionRequest.description")

        and:
        0 * auctionService.createAuction(*_)
    }


    def "should validate description size too big all good"() {
        given:
        def name = "asdasdasdasdasdasdas"
        def startsAt = "2003-03-03T03:03"
        def description = "aabove120charabove120charabove120charabove120charabove120charabove120charabove120charabove120charbove120charabove120chaabove120char"
        def price = 12.36

        def postContent = """
        |{
        |   "name": "$name",
        |   "startsAt": "$startsAt",
        |   "description": "$description",
        |   "price": $price 
        |}""".stripMargin()

        when:
        def result = mvc.perform(post(ENDPOINT)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(postContent)
        )

        then:
        result.andExpect { status().isBadRequest() }

        and:
        result.andReturn().resolvedException.message.contains("Size.auctionRequest.description")

        and:
        0 * auctionService.createAuction(*_)
    }


    def "should validate starts at null"() {
        given:
        def name = "asdasdasdasdasdasdas"
        def startsAt = null
        def description = "asdasdasdsaddasasdas"
        def price = 12.36

        def postContent = """
        |{
        |   "name": "$name",
        |   "startsAt": $startsAt,
        |   "description": "$description",
        |   "price": $price 
        |}""".stripMargin()

        when:
        def result = mvc.perform(post(ENDPOINT)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(postContent)
        )

        then:
        result.andExpect { status().isBadRequest() }

        and:
        result.andReturn().resolvedException.message.contains("NotNull.auctionRequest.startsAt")

        and:
        0 * auctionService.createAuction(*_)
    }


    def "should validate price null"() {
        given:
        def name = "asdasdasdasdasdasdas"
        def startsAt = "2003-03-03T03:03"
        def description = "asdasdasdsaddasasdas"
        def price = null

        def postContent = """
        |{
        |   "name": "$name",
        |   "startsAt": "$startsAt",
        |   "description": "$description",
        |   "price": $price 
        |}""".stripMargin()

        when:
        def result = mvc.perform(post(ENDPOINT)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(postContent)
        )

        then:
        result.andExpect { status().isBadRequest() }

        and:
        result.andReturn().resolvedException.message.contains("NotNull.auctionRequest.price")

        and:
        0 * auctionService.createAuction(*_)
    }

    def "should validate price negative"() {
        given:
        def name = "asdasdasdasdasdasdas"
        def startsAt = "2003-03-03T03:03"
        def description = "asdasdasdsaddasasdas"
        def price = -12.36D

        def postContent = """
        |{
        |   "name": "$name",
        |   "startsAt": "$startsAt",
        |   "description": "$description",
        |   "price": $price 
        |}""".stripMargin()

        when:
        def result = mvc.perform(post(ENDPOINT)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(postContent)
        )

        then:
        result.andExpect { status().isBadRequest() }

        and:
        result.andReturn().resolvedException.message.contains("Positive.auctionRequest.price")

        and:
        0 * auctionService.createAuction(*_)
    }

    def "should use filter all good"() {
        given:
        def request = PageRequest.of(0, 20, Sort.unsorted())
        def filter = new AuctionFilter(
                ["test"] as LinkedHashSet,
                Range.atLeast((LocalDateTime.of(2023, 3, 3, 3, 3))),
                null
        )

        when:
        def result = mvc.perform(get(ENDPOINT)
                .param("name", "test")
                .param("startsAt.from", "2023-03-03T03:03")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
        )

        then:
        1 * auctionService.fetchByFilter(filter, request) >> new PageImpl<Auction>([])

        and:
        result.andExpect { status().isOk() }
    }

    def "should delete by id all good"() {
        when:
        def result = mvc.perform(delete(ENDPOINT + "/1")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
        )

        then:
        1 * auctionService.deleteById(1)

        and:
        result.andExpect { status().isNoContent() }
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
