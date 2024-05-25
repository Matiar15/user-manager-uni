package org.example.item

import jakarta.persistence.*
import org.example.Auction
import org.example.category.Category

@Entity
@Table
class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    @Column
    lateinit var name: String

    @OneToOne
    @JoinColumn(name = "id_category")
    lateinit var category: Category

    @OneToOne(cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "id_auction")
    lateinit var auction: Auction

    @Column
    var price: Double? = null

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
