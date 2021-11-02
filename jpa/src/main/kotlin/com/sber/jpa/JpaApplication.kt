package com.sber.jpa

import com.sber.jpa.persistence.entity.BookAuthor
import com.sber.jpa.persistence.repository.AuthorRepository
import com.sber.jpa.persistence.entity.Book
import com.sber.jpa.persistence.entity.Language
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JpaApplication(
    private val authorRepository: AuthorRepository
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val pushkin = BookAuthor(
            surname = "Пушкин",
            language = Language(lang = "Русский")
        )
        pushkin.addBook(Book(bookTitle = "Пир во время чумы"))
        pushkin.addBook(Book(bookTitle = "Евгений Онегин"))
        pushkin.addBook(Book(bookTitle = "Русалка"))

        val doncova = BookAuthor(
            surname = "Данцова",
            language = Language(lang = "Русский")
        )
        doncova.addBook(Book(bookTitle = "Убийца - дворецкий"))
        doncova.addBook(Book(bookTitle = "Убийца - дворецкий 2"))

        val oHenry = BookAuthor(
            surname = "O'Henry",
            language = Language(lang = "English")
        )
        oHenry.addBook(Book(bookTitle = "The Ransom of Red Chief"))


        authorRepository.saveAll(listOf(pushkin, doncova, oHenry))
        var foundAll = authorRepository.findAll()
        println(foundAll)
        println("<-------------------------------------->")

        var found = authorRepository.findById(1)
        println(found)
        println("<-------------------------------------->")

        doncova.surname = "Донцова"
        authorRepository.save(doncova)
        println("Фамилия Донцовой: ${doncova.surname}")
        println("<-------------------------------------->")

        authorRepository.delete(doncova)
        var noDoncova = authorRepository.findAll()
        println(noDoncova)
        println("<-------------------------------------->")
    }
}

fun main(args: Array<String>) {
    runApplication<JpaApplication>(*args)
}
