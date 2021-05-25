package tld.sld.bookingcinemaseats.core.repositories

import tld.sld.bookingcinemaseats.core.entities.BookingEntity
import tld.sld.bookingcinemaseats.core.entities.NewBookingEntity

interface BookingRepository {
    fun getByMovieSessionId(movieSessionId: Long): List<BookingEntity>
    fun insertBooking(entity: NewBookingEntity): Boolean
}