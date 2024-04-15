package team.devlib.android.domain.util

data object BadRequestException : RuntimeException() {
    private fun readResolve(): Any = BadRequestException
}

data object UnAuthorizedException : RuntimeException() {
    private fun readResolve(): Any = UnAuthorizedException
}

data object ForbiddenException : RuntimeException() {
    private fun readResolve(): Any = ForbiddenException
}

data object NotFoundException : RuntimeException() {
    private fun readResolve(): Any = NotFoundException
}

data object MethodNotAllowedException : RuntimeException() {
    private fun readResolve(): Any = MethodNotAllowedException
}

data object ConflictException : RuntimeException() {
    private fun readResolve(): Any = ConflictException
}

data object TooManyRequestException : RuntimeException() {
    private fun readResolve(): Any = TooManyRequestException
}

data object CheckServerException : RuntimeException() {
    private fun readResolve(): Any = CheckServerException
}

data object ServerException : RuntimeException() {
    private fun readResolve(): Any = ServerException
}

data object OfflineException : RuntimeException() {
    private fun readResolve(): Any = OfflineException
}

data object ConnectionTimeOutException : RuntimeException() {
    private fun readResolve(): Any = ConnectionTimeOutException
}
