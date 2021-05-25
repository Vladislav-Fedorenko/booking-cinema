package tld.sld.bookingcinemaseats.core.repositories

import tld.sld.bookingcinemaseats.core.entities.MovieSessionEntity

interface MovieSessionRepository {
    fun getById(movieSessionId: Long): MovieSessionEntity?
}
