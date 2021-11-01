package entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Book(
    @Id
    @GeneratedValue
    var bookId: Int = 0,
    var bookTitle: String,
)
{   override fun toString(): String {
    return "Language(bookId=$bookId, lang='$bookTitle')"}
}
