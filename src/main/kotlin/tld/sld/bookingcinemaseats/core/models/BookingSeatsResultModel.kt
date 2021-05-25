package tld.sld.bookingcinemaseats.core.models

import tld.sld.bookingcinemaseats.core.entities.SeatStatus

data class BookingSeatsResultModel(
    val movieSessionId: Long,
    val bookingStatus: Map<Int, SeatStatus> = emptyMap(),
)
