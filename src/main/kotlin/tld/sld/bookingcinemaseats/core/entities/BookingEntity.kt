package tld.sld.bookingcinemaseats.core.entities

import java.sql.Timestamp

data class BookingEntity(
    val id: Long,
    val movieSessionId: Long,
    val seatNumber: Int,
    val status: SeatStatus,
    val createdAt: Timestamp,
    val updatedAt: Timestamp,
)
