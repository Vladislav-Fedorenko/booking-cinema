package tld.sld.bookingcinemaseats.core.services.seats

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import tld.sld.bookingcinemaseats.TestConfiguration
import tld.sld.bookingcinemaseats.core.repositories.*
import tld.sld.bookingcinemaseats.core.services.NotFoundException
import tld.sld.bookingcinemaseats.core.services.ServiceException
import tld.sld.bookingcinemaseats.web.models.forms.BookSeatsForm

@ActiveProfiles("test")
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = [TestConfiguration::class],
)
class BookSeatsServiceImplTest {
    @Autowired
    private lateinit var jdbc: JdbcTemplate

    @Test
    fun movieSessionRepoThrowsExceptionTest() {
        val form = BookSeatsForm(movieSessionId = 0L, seats = emptyList())
        val movieSessionRepository = mock(MovieSessionRepository::class.java)
        doAnswer { throw RepositoryException() }.`when`(movieSessionRepository).getById(anyLong())
        val service = BookSeatsServiceImpl(
            movieSessionRepository = movieSessionRepository,
            bookingRepository = mock(BookingRepository::class.java)
        )
        assertThrows(ServiceException::class.java) { service.bookSeats(form) }
    }

    @Test
    fun notFoundMissionSessionTest() {
        val form = BookSeatsForm(movieSessionId = 0L, seats = emptyList())
        val movieSessionRepository = mock(MovieSessionRepository::class.java)
        `when`(movieSessionRepository.getById(anyLong())).thenReturn(null)
        val service = BookSeatsServiceImpl(
            movieSessionRepository = movieSessionRepository,
            bookingRepository = mock(BookingRepository::class.java)
        )
        assertThrows(NotFoundException::class.java) { service.bookSeats(form) }
    }

    @Test
    fun positiveTest() {
        val service = BookSeatsServiceImpl(
            movieSessionRepository = MovieSessionRepositoryImpl(jdbc = jdbc),
            bookingRepository = BookingRepositoryImpl(jdbc = jdbc)
        )

        val form = BookSeatsForm(movieSessionId = 1L, seats = listOf(1, 2, 3, 4, -1, 300000))
        val result = service.bookSeats(form)
        assertEquals(result.movieSessionId, form.movieSessionId)
        assertTrue(result.bookingStatus.size <= form.seats.size)
    }
}