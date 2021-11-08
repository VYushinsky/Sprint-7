package com.Sber.MVC.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class LoginController {
    @GetMapping("/")
    fun redirectToLoginPage(): String {
        return "redirect:/toHome"
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