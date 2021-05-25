package tld.sld.bookingcinemaseats.core.repositories

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import tld.sld.bookingcinemaseats.core.entities.MovieSessionEntity

@Repository
class MovieSessionRepositoryImpl(private val jdbc: JdbcTemplate) : MovieSessionRepository {
    private val GET_BY_ID_QUERY = """
            SELECT
                ms.id, 
                ms.hall_id, 
                ms.datetime, 
                ms.created_at, 
                ms.updated_at,
                h.total_seats
            FROM movie_session as ms
            INNER JOIN hall as h ON ms.hall_id = h.id
            WHERE ms.id = ?
        """.trimIndent()

    override fun getById(movieSessionId: Long): MovieSessionEntity? {
        try {
            return jdbc.query(
                GET_BY_ID_QUERY,
                {rs, _ -> MovieSessionEntity(
                    id = rs.getLong(1),
                    hallId = rs.getLong(2),
                    datetime = rs.getTimestamp(3),
                    createdAt = rs.getTimestamp(4),
                    updatedAt = rs.getTimestamp(5),
                    totalSeats = rs.getInt(6),
                )
                },
                movieSessionId
            ).singleOrNull()
        } catch (e: Exception) {
            throw RepositoryException("Failed to get hall by id='${movieSessionId}'", e)
        }
    }
}