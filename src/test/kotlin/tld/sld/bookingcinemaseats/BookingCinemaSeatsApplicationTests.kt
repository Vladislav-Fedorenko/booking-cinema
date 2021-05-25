package tld.sld.bookingcinemaseats

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.MOCK,
	classes = [BookingCinemaSeatsApplicationTests::class]
)
class BookingCinemaSeatsApplicationTests {

	@Test
	fun contextLoads() {
	}

}
