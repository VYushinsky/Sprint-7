package com.Sber.MVC.servlet
/*
import java.util.*
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet("/LoginServlet")

class AuthServlet: HttpServlet() {
    private val login = "root"
    private val password = "root"

    override fun doPost(req: HttpServletRequest?, resp: HttpServletResponse?) {
        val usernamePost = req?.getParameter("login")
        val passwordPost = req?.getParameter("password")

        if(usernamePost.equals(login) && passwordPost.equals(password)) {
            val cookie = Cookie("auth", Calendar.getInstance().timeInMillis.toString())
            resp!!.addCookie(cookie)
            resp.sendRedirect("/toHome")
        } else {
            resp!!.sendRedirect("/errorLo")
        }
    }

    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        req!!.getRequestDispatcher("/auth.html").forward(req, resp)
    }
}*/