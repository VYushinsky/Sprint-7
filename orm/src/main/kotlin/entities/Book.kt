package entities

import javax.persistence.*

@Entity
class Book(
    @Id
    @GeneratedValue
    var id: Long = 0,

    @Column
    var bookTitle: String,

)
{   override fun toString(): String {
    return "Book(bookId=$id, lang='$bookTitle')"}
}
