package com.Sber.MVC


import com.Sber.MVC.model.BookData
import com.Sber.MVC.service.BookInfoService

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import javax.servlet.http.Cookie


@SpringBootTest()
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
internal class MvcTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var service: BookInfoService

    @BeforeAll
    fun setUp() {
        service.add(BookData("John", "Connor"))
        service.add(BookData("Kyle", "Rivz"))
        service.add(BookData("Sara", "Connor"))
    }

    @Test
    fun `should add record`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/app/add"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("addPage"))
        mockMvc.perform(
            MockMvcRequestBuilders.post("/app/add")
                .param("surname", "Terminator")
                .param("address", "SkyNet"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("home"))
    }

    @Test
    fun `should edit record`() {
        mockMvc.perform(MockMvcRequestBuilders
            .post("/app/2/edit")
            .param("surname", "Terminator")
            .param("address", "SkyNet"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("home"))
    }

    @Test
    fun `should show list`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/app/list"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("listPage"))
    }

    @Test
    fun `should view one record`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/app/0/view").cookie(Cookie("auth", "0")))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("viewPage"))
    }

    @Test
    fun `should delete record`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/app/1/delete"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.view().name("home"))
    }
}