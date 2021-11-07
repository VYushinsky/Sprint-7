package com.sber.jpa.persistence.entity

import javax.persistence.*

@Entity
@Table(name = "bookAuthor")
class BookAuthor(
    @Id
    @GeneratedValue
    var id: Long = 0,

    @Column
    var surname: String,

    @OneToOne(cascade = [CascadeType.ALL])
    var language: Language,

    @OneToMany(cascade = [CascadeType.ALL],
        fetch = FetchType.EAGER)

    @JoinColumn
    var book:MutableList<Book> = mutableListOf())

    {
    fun addBook(books: Book) {
        book.add(books)
    }

    override fun toString(): String {
        return "BookAuthor(id=$id, surname='$surname', " +
                "language=$language, " +
                "book=$book)"
    }
}
