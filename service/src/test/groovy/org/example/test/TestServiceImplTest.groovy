package org.example.test

import spock.lang.Specification

class TestServiceImplTest extends Specification {
    // dumb service test for convenience
    def underTest = new TestServiceImpl()

    def "should add 1 to 2"() {
        given:
        int number1 = 10
        int number2 = 20

        when:
        def result = underTest.add(number1, number2)

        then:
        result == 30
    }
}
