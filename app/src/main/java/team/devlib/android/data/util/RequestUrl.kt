package team.devlib.android.data.util

sealed interface RequestUrl {
    data object User: RequestUrl {
        const val auth = "/user/auth"
        const val user = "/user"
    }

    data object Book: RequestUrl {
        const val book = "/book"
        const val mark = "$book/mark"
    }
}
