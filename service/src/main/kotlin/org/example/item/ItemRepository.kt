package org.example.item

import org.example.exception.ItemNotFoundException
import org.springframework.data.jpa.repository.JpaRepository

interface ItemRepository : JpaRepository<Item, Int>, ItemCustomRepository

fun ItemRepository.requireById(id: Int) = findById(id).orElseThrow { ItemNotFoundException(id) }
