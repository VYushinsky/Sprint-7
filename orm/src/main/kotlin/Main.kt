import entities.Author
import entities.Book
import entities.Language
import org.hibernate.cfg.Configuration

fun main() {
    val sessionFactory = Configuration().configure()
        .addAnnotatedClass(Author::class.java)
        .addAnnotatedClass(Language::class.java)
        .addAnnotatedClass(Book::class.java)
        .buildSessionFactory()

    sessionFactory.use { sessionFactory ->
        val dao = AuthorDAO(sessionFactory)

        val pushkin = Author(
            surname = "Пушкин",
            language = Language(lang = "Русский")
        )
        pushkin.addBook(Book(bookTitle = "Пир во время чумы"))
        pushkin.addBook(Book(bookTitle = "Евгений Онегин"))
        pushkin.addBook(Book(bookTitle = "Русалка"))

        val doncova = Author(
            surname = "Донцова",
            language = Language(lang = "Русский")
        )
        doncova.addBook(Book(bookTitle = "Убийца - дворецкий"))
        doncova.addBook(Book(bookTitle = "Убийца - дворецкий 2. Возвращение дворецкого"))

        val oHenry = Author(
            surname = "O'Henry",
            language = Language(lang = "English")
        )
        oHenry.addBook(Book(bookTitle = "The Ransom of Red Chief"))


        dao.create(pushkin)
        dao.create(oHenry)
        dao.create(doncova)

        dao.delete(doncova.id)
        println("Донцова удалена")
    }
}