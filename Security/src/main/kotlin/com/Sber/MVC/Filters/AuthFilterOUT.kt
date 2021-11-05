import java.util.*
import javax.servlet.FilterChain
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
/*
@WebFilter(urlPatterns = ["/api/*", "/app/*"])

class AuthFilter: HttpFilter() {

    override fun doFilter(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?) {
        val cookies = request!!.cookies

        if(cookies == null) {
            response!!.sendRedirect("/login")
        } else {
            val currentTime = Calendar.getInstance().timeInMillis.toString()
            for(c in cookies) {
                if(c.name != "auth") {
                    response!!.sendRedirect("/login")
                } else if(c.value >= currentTime) {
                    response!!.sendRedirect("/login")
                } else {
                    chain!!.doFilter(request, response)
                }
            }
        }
    }
}*/