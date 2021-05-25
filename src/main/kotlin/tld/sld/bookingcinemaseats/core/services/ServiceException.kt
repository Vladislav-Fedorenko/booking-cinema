package tld.sld.bookingcinemaseats.core.services

class ServiceException : Exception {
    constructor() : super()
    constructor(message: String?, cause: Exception?) : super(message, cause)
    constructor(message: String?) : super(message)
    constructor(cause: Exception?) : super(cause)
}
