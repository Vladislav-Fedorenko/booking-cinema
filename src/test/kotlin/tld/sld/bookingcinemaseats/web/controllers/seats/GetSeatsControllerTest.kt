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
import org.springframework.transaction.annotation.Transactional
import tld.sld.bookingcinemaseats.TestConfiguration
import tld.sld.bookingcinemaseats.core.services.seats.GetSeatsService

@ActiveProfiles("test")
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = [TestConfiguration::class],
)
@AutoConfigureMockMvc
@Transactional
class GetSeatsControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var service: GetSeatsService

    @Test
    fun okTest() {
        mockMvc.perform(MockMvcRequestBuilders
            .get("/seats")
            .param("movieSessionId", "1")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").doesNotExist())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
    }

    @Test
    fun notFoundTest() {
        mockMvc.perform(MockMvcRequestBuilders
            .get("/seats")
            .param("movieSessionId", "0")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").doesNotExist())
            .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value(
                "Movie session with id = '0' doesn't exist"
            ))
    }
}