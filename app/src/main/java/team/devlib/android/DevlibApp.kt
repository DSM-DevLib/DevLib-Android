package team.devlib.android

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import team.devlib.android.feature.signin.SignInScreen
import team.devlib.android.feature.splash.SplashScreen

@Composable
internal fun DevlibApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.Auth.route,
    ) {
        auth(navController = navController)
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
            SignInScreen(navController = navController)
        }
    }
}
