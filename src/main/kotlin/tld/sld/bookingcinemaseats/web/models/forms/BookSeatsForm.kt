package tld.sld.bookingcinemaseats.web.models.forms

data class BookSeatsForm(
    val movieSessionId: Long,
    val seats: List<Int>,
)
