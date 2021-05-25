package tld.sld.bookingcinemaseats.web.controllers.seats

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import tld.sld.bookingcinemaseats.core.models.SeatModel
import tld.sld.bookingcinemaseats.core.services.NotFoundException
import tld.sld.bookingcinemaseats.core.services.ServiceException
import tld.sld.bookingcinemaseats.core.services.seats.GetSeatsService
import tld.sld.bookingcinemaseats.web.models.responses.BaseResponse

@Controller
class GetSeatsController(private val service: GetSeatsService) {
    @GetMapping(value = ["/seats"])
    fun getSeats(@RequestParam movieSessionId: Long): ResponseEntity<BaseResponse<List<SeatModel>>> {
        try {
            return ResponseEntity(
                BaseResponse(success = true, data = service.get(movieSessionId), errorMessage = null),
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