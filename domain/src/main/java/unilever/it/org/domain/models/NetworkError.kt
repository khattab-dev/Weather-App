package unilever.it.org.domain.models

enum class NetworkError: Error {
    REQUEST_TIMEOUT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    NOT_FOUND,
    SERVER_ERROR,
    SERIALIZATION,
    UNKNOWN,
}