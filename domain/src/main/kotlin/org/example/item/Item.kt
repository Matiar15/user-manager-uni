package org.example.item

import jakarta.persistence.*
import org.example.Auction
import org.example.category.Category
import java.time.LocalDate

@Entity
@Table
class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    @Column
    lateinit var name: String

    @ManyToOne
    @JoinColumn
    lateinit var category: Category

    @ManyToOne
    @JoinColumn
    lateinit var auction: Auction

    @Column
    var producedAt: LocalDate? = null

    @Column
    var quality: Quality? = Quality.WELL_WORN

    @Column
    var price: Double? = null

    enum class Quality {
        FACTORY_NEW,
        MINIMAL_WEAR,
        FIELD_TESTED,
        WELL_WORN,
        BATTLE_SCARRED
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Item

        return id == other.id
    }

    override fun hashCode(): Int {
        return id
    }
}
