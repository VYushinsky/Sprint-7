package entities

import javax.persistence.*

@Entity
class Author(
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
    var book:MutableList<Book> = mutableListOf()
)

{
    fun addBook(book: Book) {
        this.book.add(book)
    }

    override fun toString(): String {
        return "Language(id=$id, surname='$surname', " +
                "language=$language, " +
                "book=$book)"
    }
}
