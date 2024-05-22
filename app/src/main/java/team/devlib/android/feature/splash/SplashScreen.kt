package team.devlib.android.feature.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import team.devlib.android.R
import team.devlib.android.navigation.NavigationRoute

@Composable
internal fun SplashScreen(
    navController: NavController,
    splashViewModel: SplashViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        delay(2000L) // 로그인 속도가 빨라서 스플래시가 안보임
        splashViewModel.checkAccessToken()
        splashViewModel.sideEffect.collect {
            when (it) {
                is SplashSideEffect.Success -> {
                    navController.navigate(NavigationRoute.Main.MAIN)
                }

                else -> {
                    navController.navigate(NavigationRoute.Auth.SIGN_IN)
                }
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_devlib),
            contentDescription = null,
        )
    }
}
