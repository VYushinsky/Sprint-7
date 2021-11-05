package com.Sber.MVC.Controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry

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

}