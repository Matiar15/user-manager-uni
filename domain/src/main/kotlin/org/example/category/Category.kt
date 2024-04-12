package org.example.category

import jakarta.persistence.*

@Entity
@Table
class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0

    @Column
    lateinit var name: String
}