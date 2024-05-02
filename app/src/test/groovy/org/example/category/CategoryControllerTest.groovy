package org.example.category

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(CategoryController)
class CategoryControllerTest extends Specification {
    @Autowired
    MockMvc mvc

    @Autowired
    CategoryService categoryService

    private final String ENDPOINT = "/api/categories"

    def "should return all categories"() {
        when:
        def result = mvc.perform(
                get(ENDPOINT)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
        )

        then:
        1 * categoryService.fetch() >> []

        and:
        result.andExpect { status().isCreated() }
    }


    @TestConfiguration
    static class TestConfig {
        def factory = new DetachedMockFactory()

        @Bean
        CategoryService categoryService() {
            factory.Mock(CategoryService)
        }
    }
}
