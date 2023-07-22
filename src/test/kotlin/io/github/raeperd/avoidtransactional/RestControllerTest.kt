package io.github.raeperd.avoidtransactional

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.transaction.Transactional
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@AutoConfigureMockMvc
@SpringBootTest
class RestControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val mapper: ObjectMapper
) {
    @Test
    fun `when post article expect to get from user`() {
        var user = mockMvc.post("/users") {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsBytes(UserCreateRequest("test-user"))
        }.andExpect { status { isCreated() } }
            .andReturn().toJSON<UserDTO>()

        // this should be failed with LazyInitializationException
        val article = mockMvc.post("/users/{id}/articles", user.id) {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsBytes(ArticleCreateRequest("test-title", "test-contents"))
        }.andExpect { status { isCreated() } }
            .andReturn().toJSON<ArticleDTO>()

        user = mockMvc.get("/users/{id}", user.id)
            .andExpect { status { isOk() } }
            .andReturn().toJSON()

        assertThat(user.articles).contains(article)
    }

    private inline fun <reified T> MvcResult.toJSON(): T {
        return mapper.readValue(response.contentAsString, T::class.java)
    }
}
