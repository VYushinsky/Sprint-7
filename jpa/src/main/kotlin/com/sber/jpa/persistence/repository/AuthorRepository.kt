package com.sber.jpa.persistence.repository

import com.sber.jpa.persistence.entity.BookAuthor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorRepository : CrudRepository<BookAuthor, Long> {
}