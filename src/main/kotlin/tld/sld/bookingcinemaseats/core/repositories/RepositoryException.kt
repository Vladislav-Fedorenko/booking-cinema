package tld.sld.bookingcinemaseats.core.repositories

class RepositoryException : Exception {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Exception?) : super(message, cause)
    constructor(cause: Exception?) : super(cause)
}
