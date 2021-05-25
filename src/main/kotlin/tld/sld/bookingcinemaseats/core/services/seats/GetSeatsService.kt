package tld.sld.bookingcinemaseats.core.services.seats

import tld.sld.bookingcinemaseats.core.models.SeatModel

interface GetSeatsService {
    fun get(movieSessionId: Long): List<SeatModel>
}
