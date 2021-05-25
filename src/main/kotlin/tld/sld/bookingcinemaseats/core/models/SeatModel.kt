package tld.sld.bookingcinemaseats.core.models

import tld.sld.bookingcinemaseats.core.entities.SeatStatus

data class SeatModel(
    val bookingId: Long? = null,
    val movieSessionId: Long,
    val number: Int,
    val status: SeatStatus = SeatStatus.FREE
)
