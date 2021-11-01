package entities

import javax.persistence.*

@Entity
//@Table(name = "book")
class Author(
    @Id
    @GeneratedValue
    var id: Int = 0,
    var surname: String,
    var birthYear: Int,
    @OneToOne(cascade = [CascadeType.ALL])
    var book: Book,
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "lang")
    var language: Language
){
    override fun toString(): String {
        return "Language(id=$id, surname='$surname', birthYear=$birthYear, book=$book, language=$language)"
    }
}
