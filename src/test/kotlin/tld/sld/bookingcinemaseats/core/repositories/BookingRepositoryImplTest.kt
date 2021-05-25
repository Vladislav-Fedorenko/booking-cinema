package tld.sld.bookingcinemaseats.core.repositories

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import tld.sld.bookingcinemaseats.TestConfiguration
import tld.sld.bookingcinemaseats.core.entities.NewBookingEntity
import tld.sld.bookingcinemaseats.core.entities.SeatStatus

@ActiveProfiles("test")
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = [TestConfiguration::class],
)
class BookingRepositoryImplTest {
    @Autowired
    private lateinit var repository: BookingRepositoryImpl;

    @Test
    fun getByMovieSessionIdPositiveTest() {
        assertTrue(repository.getByMovieSessionId(1).size > 0);
        assertEquals(repository.getByMovieSessionId(0).size, 0);
    }

    @Test
    fun getByMovieSessionIdNegativeTest() {
        val jdbc = mock(JdbcTemplate::class.java)
        doAnswer {throw Exception()}.`when`(jdbc).query(anyString(), any())
        repository = BookingRepositoryImpl(jdbc = jdbc)
        assertThrows(RepositoryException::class.java) { repository.getByMovieSessionId(anyLong()) };
    }

    @Test
    fun insertBookingPositiveAndNegativeTest() {
        val newBookingEntity = NewBookingEntity(movieSessionId = 1, seatNumber = 10, status = SeatStatus.BOOKED);
        assertTrue(repository.insertBooking(newBookingEntity));
        assertThrows(RepositoryException::class.java) { repository.insertBooking(newBookingEntity) }
    }
}