package com.Sber.MVC

import com.Sber.MVC.model.BookData
import com.Sber.MVC.service.BookInfoService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.*
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.context.WebApplicationContext
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestTest{

    private val headers: HttpHeaders = HttpHeaders()
    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private val context: WebApplicationContext? = null
    @Autowired
    private lateinit var service:BookInfoService
    @Autowired
    private lateinit var testRestTemplate: TestRestTemplate

    @BeforeEach
    fun setUp() {
        val cookie = getCookie("a", "a")
        headers.add("Cookie", cookie)
        service.add(BookData("John", "Resistance"))
        service.add(BookData("Kyle", "New-York"))
        service.add(BookData("Sara", "Yellow House"))
    }

    private fun url(s: String): String {
        return "http://localhost:${port}/${s}"
    }

    fun getCookie(username: String, password: String): String {
        val map: MultiValueMap<String, String> = LinkedMultiValueMap()
        map.set("username", username)
        map.set("password", password)
        val resp: ResponseEntity<String> = testRestTemplate.postForEntity(
            url("login"),
            HttpEntity(map, HttpHeaders()), String::class.java)
        return resp.headers["Set-Cookie"]!![0]
    }

    @Test
    fun `add record`() {
        val contact = BookData("John", "Connor")
        val response = testRestTemplate.exchange("/api/add", HttpMethod.POST,
            HttpEntity(contact, headers),
            BookData::class.java)
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(contact.surname, response.body!!.surname)}

    @Test
    fun `show list`() {
        val response = testRestTemplate.exchange(
            "/api/list", HttpMethod.GET, HttpEntity<Any>(headers),
            String::class.java)
        assertEquals(response.statusCode, HttpStatus.OK)
        assertNotNull(response.body)
    }


    @Test
    fun `should edit`() {
        val entity = HttpEntity<BookData>(BookData("T800", "SkyNet"), headers)
        testRestTemplate.exchange(
            url("api/0/edit"), HttpMethod.PUT,
            entity, BookData::class.java, 1)
        assertEquals("T800", service.view(0)!!.surname)
        assertEquals("SkyNet", service.view(0)!!.address)

    }

    @Test
    fun `should view`() {
        val response = testRestTemplate.exchange(
            "/api/0/view", HttpMethod.GET, HttpEntity<Any>(headers),
            BookData::class.java)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals("Kyle", service.view(1)!!.surname)
    }
    @Test
    fun `delete record`() {
        val contact = BookData("John", "Connor")
        service.add(contact)
        testRestTemplate.exchange(
            "/api/0/delete", HttpMethod.DELETE, HttpEntity<Any>(headers),
            BookData::class.java)
        assertEquals(null, service.view(0))
    }
}