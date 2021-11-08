package com.Sber.MVC


import com.Sber.MVC.model.BookData
import com.Sber.MVC.service.BookInfoService

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import javax.servlet.http.Cookie


@SpringBootTest()
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
internal class MvcTests {

    @Autowired
    private lateinit var mockMvc: MockMvc
    @Autowired
    private val context: WebApplicationContext? = null
    @Autowired
    private lateinit var service: BookInfoService

    @BeforeAll
    fun setUp() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(context!!)
            .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
            .build()
        service.add(BookData("John", "Connor"))
        service.add(BookData("Kyle", "Riz"))
        service.add(BookData("Sara", "Connor"))
    }

    @WithMockUser(
        username = "u",
        password = "u",
        roles = ["USER"]
    )
    @Test
    fun `user could add record`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/app/add"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("addPage"))
        mockMvc.perform(
            MockMvcRequestBuilders.post("/app/add")
                .param("surname", "Terminator")
                .param("address", "SkyNet"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("home"))
    }

    @WithMockUser(
        username = "u",
        password = "u",
        roles = ["USER"]
    )
    @Test
    fun `user could not edit record`() {
        mockMvc.perform(MockMvcRequestBuilders
            .post("/app/2/edit")
                .param("surname", "Terminator")
                .param("address", "SkyNet"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isForbidden)
    }


    @WithMockUser(
        username = "u",
        password = "u",
        roles = ["USER"]
    )
    @Test
    fun `user should view list`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/app/list"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("listPage"))
    }

    @WithMockUser(
        username = "u",
        password = "u",
        roles = ["USER"]
    )
    @Test
    fun `user could view one record`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/app/0/view").cookie(Cookie("auth", "0")))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("viewPage"))
    }


    @WithMockUser(
        username = "u",
        password = "u",
        roles = ["USER"]
    )
    @Test
    fun `user should not delete record`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/app/1/delete"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isForbidden)
    }

    @WithMockUser(
        username = "a",
        password = "a",
        roles = ["ADMIN"]
    )
    @Test
    fun `admin could delete record`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/app/1/delete"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

}