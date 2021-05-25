package tld.sld.bookingcinemaseats.core.repositories

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import tld.sld.bookingcinemaseats.core.entities.BookingEntity
import tld.sld.bookingcinemaseats.core.entities.NewBookingEntity
import tld.sld.bookingcinemaseats.core.entities.SeatStatus

@Repository
class BookingRepositoryImpl(private val jdbc: JdbcTemplate) : BookingRepository {
    private val GET_BY_MOVIE_SESSION_ID_QUERY = """
            SELECT
                id,
                movie_session_id,
                seat_number,
                status,
                created_at,
                updated_at
            FROM booking
            WHERE movie_session_id = ?
        """.trimIndent()

    private val INSERT_BOOKING_QUERY = """
            INSERT INTO booking (movie_session_id, seat_number, status, created_at, updated_at)
            VALUES (?, ?, ?::seat_status_enum, now(), now())
        """.trimIndent()

    override fun getByMovieSessionId(movieSessionId: Long): List<BookingEntity> {
        try {
            return jdbc.query(
                GET_BY_MOVIE_SESSION_ID_QUERY,
                {rs, _ -> BookingEntity(
                    id = rs.getLong(1),
                    movieSessionId = rs.getLong(2),
                    seatNumber = rs.getInt(3),
                    status = SeatStatus.valueOf(rs.getString(4)),
                    createdAt = rs.getTimestamp(5),
                    updatedAt = rs.getTimestamp(6),
                ) },
                movieSessionId
            )
        } catch (e: Exception) {
            throw RepositoryException("Failed to get booking by hallId='${movieSessionId}'", e)
        }
    }

    override fun insertBooking(entity: NewBookingEntity): Boolean {
        try {
            val result = jdbc.update(
                INSERT_BOOKING_QUERY,
                entity.movieSessionId,
                entity.seatNumber,
                entity.status.toString(),
            )
            return result == 1;
        } catch (e: Exception) {
            throw RepositoryException("Failed to insert booking='${entity}'", e)
        }
    }
}
