package team.retum.network.util

import retrofit2.HttpException
import team.devlib.android.domain.util.BadRequestException
import team.devlib.android.domain.util.CheckServerException
import team.devlib.android.domain.util.ConflictException
import team.devlib.android.domain.util.ConnectionTimeOutException
import team.devlib.android.domain.util.ForbiddenException
import team.devlib.android.domain.util.MethodNotAllowedException
import team.devlib.android.domain.util.NotFoundException
import team.devlib.android.domain.util.OfflineException
import team.devlib.android.domain.util.ServerException
import team.devlib.android.domain.util.TooManyRequestException
import team.devlib.android.domain.util.UnAuthorizedException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class RequestHandler<T> {
    suspend fun request(block: suspend () -> T): T =
        try {
            block()
        } catch (e: HttpException) {
            throw when (e.code()) {
                400 -> BadRequestException
                401 -> UnAuthorizedException
                403 -> ForbiddenException
                404 -> NotFoundException
                405 -> MethodNotAllowedException
                409 -> ConflictException
                429 -> TooManyRequestException
                502 -> CheckServerException
                in 500..599 -> ServerException
                else -> e
            }
        } catch (e: SocketTimeoutException) {
            throw ConnectionTimeOutException
        } catch (e: UnknownHostException) {
            throw OfflineException
        } catch (e: Throwable) {
            throw e
        }
}
