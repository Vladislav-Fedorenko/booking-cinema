package tld.sld.bookingcinemaseats.core.entities

data class NewBookingEntity(
    val movieSessionId: Long,
    val seatNumber: Int,
    val status: SeatStatus,
)