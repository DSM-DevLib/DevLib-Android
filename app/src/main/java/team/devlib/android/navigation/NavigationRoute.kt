package team.devlib.android.navigation

sealed class NavigationRoute(val route: String) {
    data object Auth : NavigationRoute(route = "/auth") {
        val SPLASH = "$route/splash"
        val SIGN_IN = "$route/signIn"
        val SIGN_UP = "$route/signUp"
    }

    data object Root : NavigationRoute(route = "/root") {
        val HOME = "$route/home"
        val QUESTION = "$route/question"
        val PROFILE = "$route/profile"
    }

    data object Main : NavigationRoute(route = "/main") {
        val MAIN = "$route/root"
        val BOOK_DETAILS = "$route/details"
        val POST_REVIEW = "$route/postReview"
    }

    data object Arguments {
        val BOOK_ID = "{book-id}"
    }
}
