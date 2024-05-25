package org.example.item

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size
import org.example.validation.group.Patch
import org.example.validation.group.Post

data class ItemRequest(
    @get:Size.List(
        Size(max = 60, message = "itemRequest.name.size.max", groups = [Post::class, Patch::class]),
        Size(min = 10, message = "itemRequest.name.size.min", groups = [Post::class, Patch::class])
    )
    @get:NotBlank(groups = [Post::class])
    val name: String? = null,

    @get:NotNull(groups = [Post::class])
    @get:Valid
    val category: RefCategory? = null,

    @get:NotNull(groups = [Post::class])
    @get:Positive(groups = [Post::class, Patch::class])
    val price: Double? = null
)

data class RefCategory(
    @get:NotNull(groups = [Post::class])
    val id: Int? = null
)

data class RefAuction(
    @get:NotNull(groups = [Post::class])
    val id: Int? = null
)
