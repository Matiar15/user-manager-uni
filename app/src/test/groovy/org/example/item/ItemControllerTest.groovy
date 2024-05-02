package org.example.item

import org.example.Auction
import org.example.category.Category
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import java.time.LocalDate

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ItemController)
class ItemControllerTest extends Specification {
    @Autowired
    MockMvc mvc

    @Autowired
    ItemService itemService

    private final String ENDPOINT = "/api/items"

    def "should create item all good"() {
        given:
        def name = "tesuasdaadst"
        def categoryId = 1
        def auctionId = 2
        def producedAt = "2023-03-03"
        def quality = Item.Quality.FACTORY_NEW
        def price = 23.36D
        def content = """
        |{
        |   "name": "$name",
        |   "category": {
        |       "id": $categoryId
        |   },
        |   "auction": {
        |       "id": $auctionId
        |   },
        |   "producedAt": "$producedAt",
        |   "quality": "$quality",
        |   "price": $price
        |}""".stripMargin()

        when:
        def result = mvc.perform(
                post(ENDPOINT)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(content)
        )

        then:
        1 * itemService.createItem(
                name,
                categoryId,
                auctionId,
                LocalDate.of(2023, 3, 3),
                quality,
                price
        ) >> new Item().tap {
            it.name = name
            it.category = new Category(id: categoryId)
            it.auction = new Auction(id: auctionId)
            it.price = price
            it.producedAt = LocalDate.of(2023, 3, 3)
            it.quality = quality
        }

        and:
        result.andExpect { status().isCreated() }
    }

    def "should validate min name size"() {
        given:
        def name = "not10char"
        def categoryId = 1
        def auctionId = 2
        def producedAt = "2023-03-03"
        def quality = Item.Quality.FACTORY_NEW
        def price = 23.36D
        def content = """
        |{
        |   "name": "$name",
        |   "category": {
        |       "id": $categoryId
        |   },
        |   "auction": {
        |       "id": $auctionId
        |   },
        |   "producedAt": "$producedAt",
        |   "quality": "$quality",
        |   "price": $price
        |}""".stripMargin()

        when:
        def result = mvc.perform(
                post(ENDPOINT)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(content)
        )

        then:
        0 * itemService.createItem(*_)

        and:
        result.andExpect { status().isBadRequest() }

        and:
        result.andReturn().resolvedException.message.contains("Size.itemRequest.name")
    }

    def "should validate max name size"() {
        given:
        def name = "aabove60charabove60charabove60charabove60charabove60charabove60charbove60char"
        def categoryId = 1
        def auctionId = 2
        def producedAt = "2023-03-03"
        def quality = Item.Quality.FACTORY_NEW
        def price = 23.36D
        def content = """
        |{
        |   "name": "$name",
        |   "category": {
        |       "id": $categoryId
        |   },
        |   "auction": {
        |       "id": $auctionId
        |   },
        |   "producedAt": "$producedAt",
        |   "quality": "$quality",
        |   "price": $price
        |}""".stripMargin()

        when:
        def result = mvc.perform(
                post(ENDPOINT)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(content)
        )

        then:
        0 * itemService.createItem(*_)

        and:
        result.andExpect { status().isBadRequest() }

        and:
        result.andReturn().resolvedException.message.contains("Size.itemRequest.name")
    }


    def "should validate blank name"() {
        given:
        def name = "     "
        def categoryId = 1
        def auctionId = 2
        def producedAt = "2023-03-03"
        def quality = Item.Quality.FACTORY_NEW
        def price = 23.36D
        def content = """
        |{
        |   "name": "$name",
        |   "category": {
        |       "id": $categoryId
        |   },
        |   "auction": {
        |       "id": $auctionId
        |   },
        |   "producedAt": "$producedAt",
        |   "quality": "$quality",
        |   "price": $price
        |}""".stripMargin()

        when:
        def result = mvc.perform(
                post(ENDPOINT)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(content)
        )

        then:
        0 * itemService.createItem(*_)

        and:
        result.andExpect { status().isBadRequest() }

        and:
        result.andReturn().resolvedException.message.contains("NotBlank.itemRequest.name")

        and:
        result.andReturn().resolvedException.message.contains("Size.itemRequest.name")
    }

    def "should validate null category id"() {
        given:
        def name = "asdbbbasdbdas"
        def categoryId = null
        def auctionId = 2
        def producedAt = "2023-03-03"
        def quality = Item.Quality.FACTORY_NEW
        def price = 23.36D
        def content = """
        |{
        |   "name": "$name",
        |   "category": {
        |       "id": $categoryId
        |   },
        |   "auction": {
        |       "id": $auctionId
        |   },
        |   "producedAt": "$producedAt",
        |   "quality": "$quality",
        |   "price": $price
        |}""".stripMargin()

        when:
        def result = mvc.perform(
                post(ENDPOINT)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(content)
        )

        then:
        0 * itemService.createItem(*_)

        and:
        result.andExpect { status().isBadRequest() }

        and:
        result.andReturn().resolvedException.message.contains("NotNull.itemRequest.category.id")
    }

    def "should validate null category"() {
        given:
        def name = "asdbbbasdbdas"
        def auctionId = 2
        def producedAt = "2023-03-03"
        def quality = Item.Quality.FACTORY_NEW
        def price = 23.36D
        def content = """
        |{
        |   "name": "$name",
        |   "category": null,
        |   "auction": {
        |       "id": $auctionId
        |   },
        |   "producedAt": "$producedAt",
        |   "quality": "$quality",
        |   "price": $price
        |}""".stripMargin()

        when:
        def result = mvc.perform(
                post(ENDPOINT)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(content)
        )

        then:
        0 * itemService.createItem(*_)

        and:
        result.andExpect { status().isBadRequest() }

        and:
        result.andReturn().resolvedException.message.contains("NotNull.itemRequest.category")
    }


    def "should validate null auction id"() {
        given:
        def name = "asdbbbasdbdas"
        def categoryId = 2
        def auctionId = null
        def producedAt = "2023-03-03"
        def quality = Item.Quality.FACTORY_NEW
        def price = 23.36D
        def content = """
        |{
        |   "name": "$name",
        |   "category": {
        |       "id": $categoryId
        |   },
        |   "auction": {
        |       "id": $auctionId
        |   },
        |   "producedAt": "$producedAt",
        |   "quality": "$quality",
        |   "price": $price
        |}""".stripMargin()

        when:
        def result = mvc.perform(
                post(ENDPOINT)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(content)
        )

        then:
        0 * itemService.createItem(*_)

        and:
        result.andExpect { status().isBadRequest() }

        and:
        result.andReturn().resolvedException.message.contains("NotNull.itemRequest.auction.id")
    }

    def "should validate null auction"() {
        given:
        def name = "asdbbbasdbdas"
        def categoryId = 2
        def producedAt = "2023-03-03"
        def quality = Item.Quality.FACTORY_NEW
        def price = 23.36D
        def content = """
        |{
        |   "name": "$name",
        |   "category": {
        |       "id": $categoryId
        |   },
        |   "auction": null,
        |   "producedAt": "$producedAt",
        |   "quality": "$quality",
        |   "price": $price
        |}""".stripMargin()

        when:
        def result = mvc.perform(
                post(ENDPOINT)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(content)
        )

        then:
        0 * itemService.createItem(*_)

        and:
        result.andExpect { status().isBadRequest() }

        and:
        result.andReturn().resolvedException.message.contains("NotNull.itemRequest.auction")
    }


    def "should validate null producedAt"() {
        given:
        def name = "tesuasdaadst"
        def categoryId = 1
        def auctionId = 2
        def producedAt = null
        def quality = Item.Quality.FACTORY_NEW
        def price = 23.36D
        def content = """
        |{
        |   "name": "$name",
        |   "category": {
        |       "id": $categoryId
        |   },
        |   "auction": {
        |       "id": $auctionId
        |   },
        |   "producedAt": $producedAt,
        |   "quality": "$quality",
        |   "price": $price
        |}""".stripMargin()

        when:
        def result = mvc.perform(
                post(ENDPOINT)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(content)
        )

        then:
        0 * itemService.createItem(*_)

        and:
        result.andExpect { status().isBadRequest() }

        and:
        result.andReturn().resolvedException.message.contains("NotNull.itemRequest.producedAt")
    }


    def "should validate null quality"() {
        given:
        def name = "tesuasdaadst"
        def categoryId = 1
        def auctionId = 2
        def producedAt = "2023-03-03"
        def quality = null
        def price = 23.36D
        def content = """
        |{
        |   "name": "$name",
        |   "category": {
        |       "id": $categoryId
        |   },
        |   "auction": {
        |       "id": $auctionId
        |   },
        |   "producedAt": "$producedAt",
        |   "quality": $quality,
        |   "price": $price
        |}""".stripMargin()

        when:
        def result = mvc.perform(
                post(ENDPOINT)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(content)
        )

        then:
        0 * itemService.createItem(*_)

        and:
        result.andExpect { status().isBadRequest() }

        and:
        result.andReturn().resolvedException.message.contains("NotNull.itemRequest.quality")
    }

    def "should validate null price"() {
        given:
        def name = "tesuasdaadst"
        def categoryId = 1
        def auctionId = 2
        def producedAt = "2023-03-03"
        def quality = Item.Quality.FACTORY_NEW
        def price = null
        def content = """
        |{
        |   "name": "$name",
        |   "category": {
        |       "id": $categoryId
        |   },
        |   "auction": {
        |       "id": $auctionId
        |   },
        |   "producedAt": "$producedAt",
        |   "quality": "$quality",
        |   "price": $price
        |}""".stripMargin()

        when:
        def result = mvc.perform(
                post(ENDPOINT)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(content)
        )

        then:
        0 * itemService.createItem(*_)

        and:
        result.andExpect { status().isBadRequest() }

        and:
        result.andReturn().resolvedException.message.contains("NotNull.itemRequest.price")
    }


    def "should validate not positive price"() {
        given:
        def name = "tesuasdaadst"
        def categoryId = 1
        def auctionId = 2
        def producedAt = "2023-03-03"
        def quality = Item.Quality.FACTORY_NEW
        def price = -10.36
        def content = """
        |{
        |   "name": "$name",
        |   "category": {
        |       "id": $categoryId
        |   },
        |   "auction": {
        |       "id": $auctionId
        |   },
        |   "producedAt": "$producedAt",
        |   "quality": "$quality",
        |   "price": $price
        |}""".stripMargin()

        when:
        def result = mvc.perform(
                post(ENDPOINT)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(content)
        )

        then:
        0 * itemService.createItem(*_)

        and:
        result.andExpect { status().isBadRequest() }

        and:
        result.andReturn().resolvedException.message.contains("Positive.itemRequest.price")
    }

    @TestConfiguration
    static class TestConfig {
        def factory = new DetachedMockFactory()

        @Bean
        ItemService itemService() {
            factory.Mock(ItemService)
        }
    }
}
