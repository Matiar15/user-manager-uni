package org.example.exception

import org.example.exception.generic.NotFoundException

class ItemNotFoundException(id: Int) : NotFoundException("Item with ID: $id was not found")
