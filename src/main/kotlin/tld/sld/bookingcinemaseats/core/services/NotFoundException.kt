package tld.sld.bookingcinemaseats.core.services

class NotFoundException : Exception {
    constructor(message: String?, cause: Exception?) : super(message, cause)
    constructor(message: String?) : super(message)
    constructor(cause: Exception?) : super(cause)
}