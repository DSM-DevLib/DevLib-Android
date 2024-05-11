package team.devlib.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import team.devlib.android.feature.bookdetails.BookDetailsScreen
import team.devlib.android.feature.signin.SignInScreen
import team.devlib.android.feature.signup.SignUpScreen
import team.devlib.android.feature.splash.SplashScreen

@Composable
internal fun DevlibApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.Auth.route,
    ) {
        auth(navController = navController)
        main(navController = navController)
    }
}

private fun NavGraphBuilder.auth(navController: NavController) {
    navigation(
        route = NavigationRoute.Auth.route,
        startDestination = NavigationRoute.Auth.SPLASH,
    ) {
        composable(route = NavigationRoute.Auth.SPLASH) {
            SplashScreen(navController = navController)
        }

        composable(route = NavigationRoute.Auth.SIGN_IN) {
            SignInScreen(
                navigateToMain = {
                    navController.navigate(NavigationRoute.Main.MAIN) {
                        popUpTo(0)
                    }
                },
                navigateToSignUp = {
                    navController.navigate(NavigationRoute.Auth.SIGN_UP)
                }
            )
        }

        composable(route = NavigationRoute.Auth.SIGN_UP) {
            SignUpScreen(navController = navController)
        }
    }
}

private fun NavGraphBuilder.main(navController: NavController) {
    navigation(
        route = NavigationRoute.Main.route,
        startDestination = NavigationRoute.Main.MAIN,
    ) {
        composable(route = NavigationRoute.Main.MAIN) {
            RootScreen(navController = navController)
        }

        composable(
            route = "${NavigationRoute.Main.BOOK_DETAILS}/${NavigationRoute.Arguments.BOOK_ID}",
            arguments = listOf(
                navArgument(
                    name = "book-id",
                    builder = { type = NavType.LongType },
                ),
            ),
        ) {
            val bookId = it.arguments?.getLong("book-id") ?: 0L
            BookDetailsScreen(
                bookId = bookId,
                navController = navController,
            )
        }
    }
}
