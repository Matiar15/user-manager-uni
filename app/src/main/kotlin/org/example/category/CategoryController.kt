package org.example.category

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/categories")
class CategoryController(
    private val categoryService: CategoryService
) {
    @GetMapping
    fun fetch() = categoryService.fetch().map { it.toResponse() }

    private fun Category.toResponse() = CategoryResponse(id, name)
}