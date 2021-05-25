package tld.sld.bookingcinemaseats.core.services.seats

import tld.sld.bookingcinemaseats.core.models.BookingSeatsResultModel
import tld.sld.bookingcinemaseats.web.models.forms.BookSeatsForm

interface BookSeatsService {
    fun bookSeats(form: BookSeatsForm): BookingSeatsResultModel
}
