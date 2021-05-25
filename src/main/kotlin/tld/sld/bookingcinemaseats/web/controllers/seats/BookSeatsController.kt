package tld.sld.bookingcinemaseats.web.controllers.seats

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import tld.sld.bookingcinemaseats.core.models.BookingSeatsResultModel
import tld.sld.bookingcinemaseats.core.services.NotFoundException
import tld.sld.bookingcinemaseats.core.services.ServiceException
import tld.sld.bookingcinemaseats.core.services.seats.BookSeatsService
import tld.sld.bookingcinemaseats.web.models.forms.BookSeatsForm
import tld.sld.bookingcinemaseats.web.models.responses.BaseResponse

@Controller
class BookSeatsController(private val service: BookSeatsService) {
    @PostMapping(value = ["/seats"])
    fun bookSeats(@RequestBody form: BookSeatsForm): ResponseEntity<BaseResponse<BookingSeatsResultModel>> {
        try {
            return ResponseEntity(
                BaseResponse(success = true, data = service.bookSeats(form), errorMessage = null),
                HttpStatus.OK
            )
        } catch (e: NotFoundException) {
            return ResponseEntity(
                BaseResponse(success = false, data = null, errorMessage = e.message),
                HttpStatus.NOT_FOUND
            )
        } catch (e: ServiceException) {
            return ResponseEntity(
                BaseResponse(success = false, data = null, errorMessage = e.message),
                HttpStatus.UNPROCESSABLE_ENTITY
            )
        }
    }
}