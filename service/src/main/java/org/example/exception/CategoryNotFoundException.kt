package org.example.exception

import org.example.exception.generic.NotFoundException

class CategoryNotFoundException(id: Int) : NotFoundException("Category with id $id was not found")
