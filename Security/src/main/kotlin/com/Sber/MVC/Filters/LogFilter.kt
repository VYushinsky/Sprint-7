package com.Sber.MVC.Filters

import java.util.*
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest


@WebFilter(urlPatterns = ["/*"])
class LogFilter : Filter {
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val c = java.util.Calendar.getInstance()
        val hour: Int = c.get(Calendar.HOUR_OF_DAY)
        val minute: Int = c.get(Calendar.MINUTE)
        val second: Int = c.get(Calendar.SECOND)
        if (request is HttpServletRequest)
            println("Request: ${request.requestURI}")
            println("TIME: $hour : $minute : $second")
            println("<-------------------------------------->")
        chain!!.doFilter(request, response)
    }

}