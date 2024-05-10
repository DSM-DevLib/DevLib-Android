package team.devlib.android.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import team.devlib.android.component.DmsBottomAppBar
import team.devlib.android.feature.home.HomeScreen
import team.devlib.android.feature.mypage.MyPageScreen

@Composable
internal fun RootScreen(
    navController: NavController,
) {
    val navHostController = rememberNavController()

    Scaffold(
        bottomBar = {
            DmsBottomAppBar(navController = navHostController)
        }
    ) {
        NavHost(
            modifier = Modifier.padding(it),
            navController = navHostController,
            startDestination = NavigationRoute.Root.HOME,
        ) {
            composable(route = NavigationRoute.Root.HOME) {
                HomeScreen(navController = navController)
            }

            composable(route = NavigationRoute.Root.QUESTION) {

            }

            composable(route = NavigationRoute.Root.PROFILE) {
                MyPageScreen(navController = navHostController)
            }
        }
    }
}
