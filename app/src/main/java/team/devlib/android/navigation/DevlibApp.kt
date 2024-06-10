package team.devlib.android.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import team.devlib.android.feature.bookdetails.BookDetailsScreen
import team.devlib.android.feature.home.HomeViewModel
import team.devlib.android.feature.question.CreateQuestionScreen
import team.devlib.android.feature.question.QuestionDetailsScreen
import team.devlib.android.feature.reply.CreateReplyScreen
import team.devlib.android.feature.reply.SelectBookScreen
import team.devlib.android.feature.signin.SignInScreen
import team.devlib.android.feature.signup.SignUpScreen
import team.devlib.android.feature.splash.SplashScreen

@Composable
internal fun DevlibApp() {
    val navController = rememberNavController()
    val homeViewModel = hiltViewModel<HomeViewModel>()
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.Auth.route,
    ) {
        auth(navController = navController)
        main(
            navController = navController,
            viewModel = homeViewModel
        )
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

private fun NavGraphBuilder.main(
    navController: NavController,
    viewModel: HomeViewModel,
) {
    navigation(
        route = NavigationRoute.Main.route,
        startDestination = NavigationRoute.Main.MAIN,
    ) {
        composable(route = NavigationRoute.Main.MAIN) {
            RootScreen(navController = navController)
        }

        composable(
            route = "${NavigationRoute.Main.QUESTION_DETAILS}/${NavigationRoute.Arguments.QUESTION_ID}",
            arguments = listOf(
                navArgument(
                    name = "question-id",
                    builder = { type = NavType.LongType },
                ),
            ),
        ) {
            val questionId = it.arguments?.getLong("question-id") ?: 0L
            QuestionDetailsScreen(
                navController = navController,
                questionId = questionId,
            )
        }

        composable(
            route = "${NavigationRoute.Main.CREATE_REPLY}/${NavigationRoute.Arguments.QUESTION_ID}",
            arguments = listOf(
                navArgument(
                    name = "question-id",
                    builder = { type = NavType.LongType },
                )
            )
        ) {
            val questionId = it.arguments?.getLong("question-id") ?: 0L
            CreateReplyScreen(
                navController = navController,
                questionId = questionId,
                viewModel = viewModel,
            )
        }

        composable(route = NavigationRoute.Main.CREATE_QUESTION) {
            CreateQuestionScreen(navController = navController)
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

        composable(route = NavigationRoute.Main.SELECT_BOOK) {
            SelectBookScreen(
                navController = navController,
                viewModel = viewModel,
            )
        }
    }
}
