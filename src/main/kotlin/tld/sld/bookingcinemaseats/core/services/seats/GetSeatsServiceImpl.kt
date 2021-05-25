package tld.sld.bookingcinemaseats.core.services.seats

import org.springframework.stereotype.Service
import tld.sld.bookingcinemaseats.core.entities.SeatStatus
import tld.sld.bookingcinemaseats.core.models.SeatModel
import tld.sld.bookingcinemaseats.core.repositories.BookingRepository
import tld.sld.bookingcinemaseats.core.repositories.MovieSessionRepository
import tld.sld.bookingcinemaseats.core.repositories.RepositoryException
import tld.sld.bookingcinemaseats.core.services.NotFoundException
import tld.sld.bookingcinemaseats.core.services.ServiceException

@Service
class GetSeatsServiceImpl(
    private val bookingRepository: BookingRepository,
    private val movieSessionRepository: MovieSessionRepository,
) : GetSeatsService {
    override fun get(movieSessionId: Long): List<SeatModel> {
        try {
            val movieSession = movieSessionRepository.getById(movieSessionId)
            if (movieSession == null) {
                throw NotFoundException("Movie session with id = '${movieSessionId}' doesn't exist")
            }
            val seats = MutableList<SeatModel>(
                size = movieSession.totalSeats,
                init = { index -> SeatModel(movieSessionId = movieSessionId, number = index + 1) }
            )
            val bookings = bookingRepository.getByMovieSessionId(movieSessionId)
            bookings.forEach() {
                seats[it.seatNumber - 1] = SeatModel(
                    bookingId = it.id,
                    movieSessionId = it.movieSessionId,
                    number = it.seatNumber,
                    status = SeatStatus.BOOKED
                )
            }
            return seats
        } catch (e: RepositoryException) {
            throw ServiceException("Failed to get seats of the movie session with id = '${movieSessionId}'", e)
        }
    }
}