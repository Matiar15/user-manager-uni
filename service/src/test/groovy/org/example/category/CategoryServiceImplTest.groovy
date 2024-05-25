package org.example.category

import spock.lang.Specification

class CategoryServiceImplTest extends Specification {
    CategoryRepository categoryRepository = Mock()

    def underTest = new CategoryServiceImpl(categoryRepository)

    def "should fetch all good"() {
        when:
        underTest.fetch()

        then:
        1 * categoryRepository.findAll() >> []

        and:
        0 * _
    }
}
