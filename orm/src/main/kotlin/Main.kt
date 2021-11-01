import entities.Author
import entities.Book
import entities.Lang
import entities.Language
import org.hibernate.cfg.Configuration

fun main() {
    val sessionFactory = Configuration().configure()
        .addAnnotatedClass(Language::class.java)
        .addAnnotatedClass(Author::class.java)
        .addAnnotatedClass(Book::class.java)
        .buildSessionFactory()

    sessionFactory.use { sessionFactory ->
        val dao = AuthorDAO(sessionFactory)

        val lang = Lang()
        val rus = lang.rus()
        val eng = lang.eng()
        val ger = lang.ger()


        val pushkin = Author(
            surname = "Pushkin",
            birthYear = 1799,
            book = Book(bookTitle = "Сборник стихов"),
            language = rus
        )

        val doncova = Author(
            surname = "Doncova",
            birthYear = 1952,
            book = Book(bookTitle = "Убийца - дворецкий"),
            language = rus
        )

        val oHenry = Author(
            surname = "O'Henry",
            birthYear = 1860,
            book = Book(bookTitle = "The Ransom of Red Chief"),
            language = eng
        )
        val remarque = Author(
            surname = "Erich Maria Remarque",
            birthYear = 1898,
            book = Book(bookTitle = "Im Westen nichts Neues"),
            language = ger
        )

        dao.create(pushkin)
        dao.create(oHenry)
        dao.create(doncova)
        dao.create(remarque)

    }
}