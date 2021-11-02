package entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Language(
    @Id
    @GeneratedValue
    var id: Long = 0,

    @Column
    var lang: String,
)
{
    override fun toString(): String {
        return "Language(id=$id, lang='$lang')"
    }
}