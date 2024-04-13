package org.example.category

import org.example.exception.CategoryNotFoundException
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Int>

fun CategoryRepository.requireById(id: Int) = findById(id).orElseThrow { CategoryNotFoundException(id) }