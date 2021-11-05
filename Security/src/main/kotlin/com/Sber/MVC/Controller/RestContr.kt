package com.Sber.MVC.Controller

import com.Sber.MVC.Model.BookData
import com.Sber.MVC.service.BookInfoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*



@RestController
@RequestMapping("/api")
class RestContr @Autowired constructor(val service: BookInfoService) {

    @PostMapping("/add")
    fun addRecord(@RequestBody contact: BookData): ResponseEntity<BookData> {
        service.add(contact)
        return ResponseEntity(contact, HttpStatus.CREATED)
    }

    @GetMapping("/list")
    fun showList(@RequestParam(required = false) surname: String?,
                @RequestParam(required = false) address: String?): ResponseEntity<Collection<BookData>> {
        return ResponseEntity(service.list(surname, address), HttpStatus.OK)
    }

    @GetMapping("/{id}/view")
    fun showView(@PathVariable("id") id: Int): ResponseEntity<BookData> {
        val contactViewed = service.view(id)
        return ResponseEntity(contactViewed, HttpStatus.OK)
    }

    @PutMapping("/{id}/edit")
    fun editRecord(@PathVariable id: Int, @RequestBody contact: BookData): ResponseEntity<BookData> {
        val editedContact = service.edit(id, contact)
        return ResponseEntity(editedContact, HttpStatus.OK)
    }

    @DeleteMapping("/{id}/delete")
    fun deleteRecord(@PathVariable("id") id: Int): ResponseEntity<BookData> {
        val deleteContact = service.delete(id)
        return ResponseEntity(deleteContact, HttpStatus.OK)
    }
}