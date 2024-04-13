package org.example.category

import org.springframework.stereotype.Service

interface CategoryService {
    fun fetch(): List<Category>
}

@Service
class CategoryServiceImpl(
    private val categoryRepository: CategoryRepository
) : CategoryService {
    override fun fetch(): List<Category> = categoryRepository.findAll()
}
