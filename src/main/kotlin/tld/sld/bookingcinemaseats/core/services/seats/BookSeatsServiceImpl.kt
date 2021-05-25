package tld.sld.bookingcinemaseats.core.services.seats

import org.springframework.stereotype.Service
import tld.sld.bookingcinemaseats.core.entities.NewBookingEntity
import tld.sld.bookingcinemaseats.core.entities.SeatStatus
import tld.sld.bookingcinemaseats.core.models.BookingSeatsResultModel
import tld.sld.bookingcinemaseats.core.repositories.BookingRepository
import tld.sld.bookingcinemaseats.core.repositories.MovieSessionRepository
import tld.sld.bookingcinemaseats.core.repositories.RepositoryException
import tld.sld.bookingcinemaseats.core.services.NotFoundException
import tld.sld.bookingcinemaseats.core.services.ServiceException
import tld.sld.bookingcinemaseats.web.models.forms.BookSeatsForm

@Service
class BookSeatsServiceImpl(
    private val movieSessionRepository: MovieSessionRepository,
    private val bookingRepository: BookingRepository,
) : BookSeatsService {
    override fun bookSeats(form: BookSeatsForm): BookingSeatsResultModel {
        try {
            val movieSession = movieSessionRepository.getById(form.movieSessionId)
            if (movieSession == null) {
                throw NotFoundException("Movie session with id = '${form.movieSessionId}' doesn't exist")
            }
            val bookingStatus = mutableMapOf<Int, SeatStatus>()
            for (seatNumber in form.seats) {
                if (bookingStatus[seatNumber] != null) continue
                if (seatNumber <= 0 || seatNumber > movieSession.totalSeats) {
                    bookingStatus[seatNumber] = SeatStatus.FAILED
                    continue
                }
                try {
                    val newBookingEntity = NewBookingEntity(
                        movieSessionId = form.movieSessionId,
                        seatNumber = seatNumber,
                        status = SeatStatus.BOOKED
                    )
                    if (bookingRepository.insertBooking(newBookingEntity)) {
                        bookingStatus[seatNumber] = SeatStatus.BOOKED
                    }
                } catch (e: RepositoryException) {
                    bookingStatus[seatNumber] = SeatStatus.FAILED
                }
            }
            return BookingSeatsResultModel(
                movieSessionId = form.movieSessionId,
                bookingStatus = bookingStatus,
            )
        } catch (e: RepositoryException) {
            throw ServiceException("Failed to book the seats=${form.seats} for the movie session with id = '${form.movieSessionId}'", e)
        }
    }
}
