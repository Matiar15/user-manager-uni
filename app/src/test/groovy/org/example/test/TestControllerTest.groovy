package org.example.test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(TestController.class)
class TestControllerTest extends Specification {
    // dumb test for convenience
    @Autowired
    MockMvc mvc

    def "should get hello world all good"() {
        when:
        def result = mvc.perform(get("/test")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )

        then:
        result.andExpect(status().isOk())

        and:
        result.andReturn().response.getContentAsString() == "Hello world!"
    }
}
