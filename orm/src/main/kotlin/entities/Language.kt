package entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Language(
    @Id
    @GeneratedValue
    var authorId: Int = 0,
    val lang: String
    )
{   override fun toString(): String {
    return "Language(authorId=$authorId, lang='$lang')"}
}

class Lang{
    fun rus(): Language {
        return Language(lang = "Русский")
    }
    fun eng(): Language {
        return Language(lang = "English")
    }
    fun ger(): Language{
        return Language(lang = "German")
    }
}