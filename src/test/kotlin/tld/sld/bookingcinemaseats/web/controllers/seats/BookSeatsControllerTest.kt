package tld.sld.bookingcinemaseats.web.controllers.seats

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import tld.sld.bookingcinemaseats.TestConfiguration
import tld.sld.bookingcinemaseats.core.services.seats.BookSeatsService


@ActiveProfiles("test")
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = [TestConfiguration::class],
)
@AutoConfigureMockMvc
class BookSeatsControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var service: BookSeatsService

    @Test
    fun okTest() {
        val form = """
        {
            "movieSessionId": 1,
            "seats": [0, 1, 2, 2, 1, 1000, -17, 0]
        }
        """.trimIndent()
        mockMvc.perform(MockMvcRequestBuilders
            .post("/seats")
            .content(form)
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").doesNotExist())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.movieSessionId").value("1"))
    }

    @Test
    fun emptySeatsListOkTest() {
        val form = """
        {
            "movieSessionId": 1,
            "seats": []
        }
        """.trimIndent()
        mockMvc.perform(MockMvcRequestBuilders
            .post("/seats")
            .content(form)
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").doesNotExist())
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.movieSessionId").value("1"))
    }

    @Test
    fun notFoundTest() {
        val form = """
        {
            "movieSessionId": 0,
            "seats": [0, 1, 2, 2, 1, 1000, -17, 0]
        }
        """.trimIndent()
        mockMvc.perform(MockMvcRequestBuilders
            .post("/seats")
            .content(form)
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").doesNotExist())
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value(
                "Movie session with id = '0' doesn't exist"
            ))
    }
}