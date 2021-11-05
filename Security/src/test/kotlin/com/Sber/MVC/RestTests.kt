package com.Sber.MVC

import com.Sber.MVC.Model.BookData
import com.Sber.MVC.service.BookInfoService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.*
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import java.util.concurrent.ConcurrentHashMap

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestTests {

    private val headers: HttpHeaders = HttpHeaders()
    @LocalServerPort
    private var port: Int = 0
    @Autowired
    private lateinit var service:BookInfoService
    @Autowired
    private lateinit var testRestTemplate: TestRestTemplate
    private fun url(s: String): String {
        return "http://localhost:${port}/${s}"
    }

    private fun getCookie(login: String, password: String, loginUrl: String): String? {
        val newMap: MultiValueMap<String, String> = LinkedMultiValueMap()
        newMap.set("login", login)
        newMap.set("password", password)
        val loginResponse: ResponseEntity<String> =
            testRestTemplate.postForEntity(
                loginUrl,
                HttpEntity(newMap, HttpHeaders()),
                String::class.java)
        return loginResponse.headers["Set-Cookie"]!![0]}

    @BeforeEach
    fun setCookie() {
        val cookie = getCookie("root", "root", "/LoginServlet")
        headers.add("Cookie", cookie)
    }

    @Test
    fun `add record`() {
        val contact = BookData("John", "Connor")
        val response = testRestTemplate.exchange(
            "/api/add",
            HttpMethod.POST,
            HttpEntity(contact, headers),
            BookData::class.java)
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(contact.surname, response.body!!.surname)}

    @Test
    fun `show list`() {
        val contact: ConcurrentHashMap<Int, BookData> = ConcurrentHashMap()
        contact.put(1, BookData("John", "Connor"))
        val response = testRestTemplate.exchange("/api/list", HttpMethod.GET,
            HttpEntity(contact.values, headers), Collection::class.java)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)
    }

    @Test
    fun `should edit`() {
        val contact = BookData("John", "Connor")
        service.add(contact)
        val entity = BookData("T800", "Connor")
        val response = testRestTemplate.exchange(
            url("/api/0/edit"),
            HttpMethod.PUT,
            HttpEntity(entity, headers),
            BookData::class.java)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals("T800", service.view(0)!!.surname)
        assertEquals("Connor", service.view(0)!!.address)
    }

    @Test
    fun `should view`() {
        val contact = BookData("John", "Connor")
        service.add(contact)
        val response = testRestTemplate.exchange(
            url("api/1/view"),
            HttpMethod.GET,
            HttpEntity(null, headers),
            BookData::class.java)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals("John", service.view(1)!!.surname)
    }

    @Test
    fun `delete record`() {
        val contact = BookData("John", "Connor")
        service.add(contact)
        val response = testRestTemplate.exchange(
            url("/api/0/delete"),
            HttpMethod.DELETE,
            HttpEntity(null, headers),
            BookData::class.java)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(null, service.view(0))
    }
}


