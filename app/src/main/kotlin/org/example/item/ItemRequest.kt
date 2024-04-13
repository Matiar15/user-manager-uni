package org.example.item

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class ItemRequest(
    @get:Size.List(
        Size(max = 60, message = "itemRequest.name.size.max"),
        Size(min = 10, message = "itemRequest.name.size.min")
    )
    @get:NotBlank
    val name: String? = null,

    @get:NotNull
    @get:Valid
    val category: RefCategory? = null,

    @get:NotNull
    @get:Valid
    val auction: RefAuction? = null,

    @get:NotNull
    val producedAt: LocalDate? = null,

    @get:NotNull
    val quality: Item.Quality? = null,

    @get:NotNull
    @get:Positive
    val price: Double? = null
)

data class RefCategory(
    @get:NotNull
    val id: Int? = null
)

data class RefAuction(
    @get:NotNull
    val id: Int? = null
)
