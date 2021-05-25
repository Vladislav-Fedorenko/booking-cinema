package tld.sld.bookingcinemaseats.core.repositories

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import tld.sld.bookingcinemaseats.TestConfiguration

@ActiveProfiles("test")
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = [TestConfiguration::class],
)
class MovieSessionRepositoryImplTest {
    @Autowired
    private lateinit var repository: MovieSessionRepositoryImpl;

    @Test
    fun getByIdPositiveTest() {
        assertNotNull(repository.getById(1L))
        assertNull(repository.getById(0L))
    }

    @Test
    fun getByIdNegativeTest() {
        val jdbc = mock(JdbcTemplate::class.java)
        doAnswer { throw Exception() }.`when`(jdbc).query(anyString(), any())
        repository = MovieSessionRepositoryImpl(jdbc = jdbc)
        assertThrows(RepositoryException::class.java) { repository.getById(anyLong()) };
    }
}