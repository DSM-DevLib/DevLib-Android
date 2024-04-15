package team.devlib.android.data.util

sealed interface RequestUrl {
    data object User: RequestUrl {
        const val auth = "/user/auth"
    }
}
