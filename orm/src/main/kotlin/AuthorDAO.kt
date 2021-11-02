import entities.Author
import org.hibernate.SessionFactory


class AuthorDAO(private val sessionFactory: SessionFactory) {
    fun create(author: Author) {
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            session.save(author)
            session.transaction.commit()
        }
    }

    fun delete(id: Long) {
        val author: Author?
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            author = session.get(Author::class.java, id)
            session.delete(author)
            session.transaction.commit()
        }
    }

    fun find(id: Long): Author? {
        val result: Author?
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            result = session.get(Author::class.java, id)
            session.transaction.commit()
        }
        return result
    }

    fun findAll(): List<Author> {
        val result: List<Author>
        sessionFactory.openSession().use { session ->
            session.beginTransaction()
            result = session.createQuery("from Author").list() as List<Author>
            session.transaction.commit()
        }
        return result
    }
}
