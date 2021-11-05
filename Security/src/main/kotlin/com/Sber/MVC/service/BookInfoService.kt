package com.Sber.MVC.service

import com.Sber.MVC.Model.BookData
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class BookInfoService {
    val addressBook: ConcurrentHashMap<Int, BookData> = ConcurrentHashMap()
    var count = 0

    fun add(bookData: BookData) {
        addressBook[count++] = bookData
    }

    fun list(surname: String?, address: String?): Collection<BookData> {
        val sortedBook = ConcurrentHashMap<Int, BookData>()
        addressBook.forEach { record ->
            if (record.value.surname == surname || record.value.address == address)
                sortedBook[record.key] = record.value
        }
        return addressBook.values
    }

    fun view(id: Int): BookData? = addressBook[id]

    fun edit(id: Int, bookData: BookData): BookData? {
        if (bookData.surname.isNotEmpty())
            addressBook[id]!!.surname = bookData.surname
        if (bookData.address.isNotEmpty())
            addressBook[id]!!.address = bookData.address
        return addressBook[id]
    }

    fun delete(id: Int) = addressBook.remove(id)
}
