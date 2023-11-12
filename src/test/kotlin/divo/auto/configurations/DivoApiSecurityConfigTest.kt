package divo.auto.configurations

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.security.test.context.support.WithMockUser

@SpringBootTest
@ActiveProfiles("development")
@AutoConfigureMockMvc
class DivoApiSecurityConfigTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `access to public endpoint should be allowed`() {
        mockMvc.perform(post("/api/login"))
            .andExpect(status().isCreated)
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = ["ROLE_USER"])
    fun `access to secured endpoint should be allowed for authenticated user`() {
        mockMvc.perform(get("/api"))
            .andExpect(status().isOk)
    }

    @Test
    fun `access to secured endpoint should be denied for unauthenticated user`() {
        mockMvc.perform(get("/api"))
            .andExpect(status().isUnauthorized)
    }
}