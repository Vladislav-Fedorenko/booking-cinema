package tld.sld.bookingcinemaseats.web.models.responses

class BaseResponse<T> {
    var success: Boolean = false
    var data: T? = null
    var errorMessage: String? = null

    constructor(success: Boolean, data: T?, errorMessage: String?) {
        this.success = success
        this.data = data
        this.errorMessage = errorMessage
    }

    override fun toString(): String {
        return "BaseResponse(success=$success, data=$data, errorMessage=$errorMessage)"
    }

}