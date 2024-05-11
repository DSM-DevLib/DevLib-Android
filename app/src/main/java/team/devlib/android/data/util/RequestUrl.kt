package team.devlib.android.data.util

sealed interface RequestUrl {
    data object User: RequestUrl {
        const val auth = "/user/auth/"
        const val user = "/user/"
    }

    data object Book: RequestUrl {
        const val book = "/book"
        const val mark = "$book/mark"
        const val rank = "$book/rank"
        const val details = "$book/{book-id}"
        const val bookmark = "$book/{book-id}/mark"
        const val review = "$book/{book-id}/review"
    }

    data object Question: RequestUrl {
        const val question = "/"
    }
}
