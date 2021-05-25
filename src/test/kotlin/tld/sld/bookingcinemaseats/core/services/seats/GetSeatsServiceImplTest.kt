package tld.sld.bookingcinemaseats.core.services.seats

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.doAnswer
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import tld.sld.bookingcinemaseats.TestConfiguration
import tld.sld.bookingcinemaseats.core.entities.MovieSessionEntity
import tld.sld.bookingcinemaseats.core.repositories.*
import tld.sld.bookingcinemaseats.core.services.NotFoundException
import tld.sld.bookingcinemaseats.core.services.ServiceException
import java.sql.Timestamp
import java.time.Instant

@ActiveProfiles("test")
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = [TestConfiguration::class],
)
class GetSeatsServiceImplTest {
    @Autowired
    private lateinit var jdbc: JdbcTemplate

    @Test
    fun bookingRepoThrowsExceptionTest() {
        val movieSessionRepository = mock(MovieSessionRepository::class.java)
        `when`(movieSessionRepository.getById(anyLong())).thenReturn(MovieSessionEntity(
            id = 1,
            hallId = 1,
            datetime = Timestamp.from(Instant.now()),
            createdAt = Timestamp.from(Instant.now()),
            updatedAt = Timestamp.from(Instant.now()),
            totalSeats = 10,
        ))
        val bookingRepository = mock(BookingRepository::class.java)
        doAnswer { throw RepositoryException() }.`when`(bookingRepository).getByMovieSessionId(anyLong())
        val service = GetSeatsServiceImpl(
            movieSessionRepository = movieSessionRepository,
            bookingRepository = bookingRepository
        )
        assertThrows(ServiceException::class.java) { service.get(2L) }
    }

    @Test
    fun movieSessionRepoThrowsExceptionTest() {
        val movieSessionRepository = mock(MovieSessionRepository::class.java)
        doAnswer { throw RepositoryException() }.`when`(movieSessionRepository).getById(anyLong())
        val service = GetSeatsServiceImpl(
            movieSessionRepository = movieSessionRepository,
            bookingRepository = mock(BookingRepository::class.java)
        )
        assertThrows(ServiceException::class.java) { service.get(2L) }
    }

    @Test
    fun notFoundMovieSessionTest() {
        val movieSessionRepository = mock(MovieSessionRepository::class.java)
        `when`(movieSessionRepository.getById(anyLong())).thenReturn(null)
        val service = GetSeatsServiceImpl(
            movieSessionRepository = movieSessionRepository,
            bookingRepository = mock(BookingRepository::class.java)
        )
        assertThrows(NotFoundException::class.java) { service.get(1L) }
    }

    @Test
    fun positiveTest() {
        val service = GetSeatsServiceImpl(
            movieSessionRepository = MovieSessionRepositoryImpl(jdbc = jdbc),
            bookingRepository = BookingRepositoryImpl(jdbc = jdbc)
        )
        assertTrue(service.get(1L).size > 0)
    }
}