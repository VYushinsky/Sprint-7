package com.Sber.MVC.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@Controller
class LoginController {

    @GetMapping("/")
    fun redirectToLoginPage(): String {
        return "redirect:/toHome"
    }

    @GetMapping("/login")
    fun toLoginPage(): String {
        return "login"
    }

    @GetMapping("/errorLo")
    fun toErrorPage(): String {
        return "/errorLog"
    }

    @GetMapping("/toHome")
    fun toHomePage(): String {
        return "/home"
    }

    @GetMapping("/toAccessDenied")
    fun toAccessDeniedPage(): String {
        return "/accessDenied"
    }

}