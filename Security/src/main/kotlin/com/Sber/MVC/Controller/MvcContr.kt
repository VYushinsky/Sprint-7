package com.Sber.MVC.Controller

import com.Sber.MVC.Model.BookData
import com.Sber.MVC.service.BookInfoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/app")
class MvcContr @Autowired constructor(val service: BookInfoService) {

    @GetMapping("/add")
    fun showAddPage(): String = "addPage"

    @PostMapping("/add")
    fun addRecord(@ModelAttribute form: BookData, model: Model): String {
        service.add(form)
        return "home"
    }

    @GetMapping("/{id}/edit")
    fun showEditPage(@PathVariable("id") id: Int, model: Model): String {
        return "editPage"
    }

    @GetMapping("/list")
    fun showListPage(model: Model, @RequestParam(required = false) surname: String?, @RequestParam(required = false) address: String?): String {
        model.addAttribute("users", service.list(surname, address))
        return "listPage"
    }

    @GetMapping("/{id}/view")
    fun showViewPage(@PathVariable("id") id: Int, model: Model,): String {
        model.addAttribute("user", service.view(id))
        return "viewPage"
    }

    @GetMapping("/{id}/delete")
    fun deleteRecord(@PathVariable("id") id: Int, model: Model): String {
        service.delete(id)
        return "home"
    }

    @PostMapping("/{id}/edit")
    fun editRecord(@PathVariable("id") id: Int, @ModelAttribute form: BookData): String {
        service.edit(id, BookData(form.surname, form.address))
        return "home"
    }
}