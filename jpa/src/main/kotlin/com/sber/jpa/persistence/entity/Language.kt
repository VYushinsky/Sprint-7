package com.sber.jpa.persistence.entity

import javax.persistence.*

@Entity
@Table(name = "language")
class Language(
    @Id
    @GeneratedValue

    var id: Long = 0,

    var lang: String,
)
{
    override fun toString(): String {
        return "Language(id=$id, lang='$lang')"
    }
}