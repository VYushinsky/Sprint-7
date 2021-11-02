package com.sber.jpa.persistence.entity

import javax.persistence.*

@Entity
@Table(name = "book")
class Book(
    @Id
    @GeneratedValue
    var id: Long = 0,

    @Column
    var bookTitle: String,

)
{   override fun toString(): String {
    return "Book(id=$id, bookTitle='$bookTitle')"}
}
