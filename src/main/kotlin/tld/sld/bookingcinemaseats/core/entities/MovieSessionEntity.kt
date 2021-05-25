package tld.sld.bookingcinemaseats.core.entities

import java.sql.Timestamp

data class MovieSessionEntity (
    val id: Long,
    val hallId: Long,
    val datetime: Timestamp,
    val createdAt: Timestamp,
    val updatedAt: Timestamp,

    val totalSeats: Int,
)
