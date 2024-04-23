package team.devlib.android.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import team.devlib.android.navigation.NavigationRoute
import team.devlib.designsystem.ui.DmsTheme
import team.devlib.designsystem.ui.PaddingDefaults

@Composable
internal fun DmsBottomAppBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    BottomAppBar(
        containerColor = DmsTheme.colorScheme.surface,
        modifier = modifier.graphicsLayer {
            clip = true
            shape = RoundedCornerShape(
                topStart = 24.dp,
                topEnd = 24.dp,
            )
            shadowElevation = 20f
        },
        contentPadding = PaddingValues(
            top = PaddingDefaults.ExtraSmall,
            start = PaddingDefaults.Small,
            end = PaddingDefaults.Small,
        ),
    ) {
        MainSections.entries.forEach { section ->
            val selected = currentRoute == section.route
            NavigationBarItem(
                selected = selected,
                onClick = { navController.navigate(section.route) },
                icon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = section.iconRes),
                        contentDescription = section.label,
                    )
                },
                label = {
                    Text(text = section.label)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = DmsTheme.colorScheme.onSurface,
                    selectedTextColor = DmsTheme.colorScheme.onSurface,
                    indicatorColor = DmsTheme.colorScheme.surface,
                    unselectedIconColor = DmsTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = DmsTheme.colorScheme.onSurfaceVariant,
                ),
            )
        }
    }
}

@Immutable
private enum class MainSections(
    val route: String,
    @DrawableRes val iconRes: Int,
    val label: String,
) {
    HOME(
        route = NavigationRoute.Root.HOME,
        iconRes = team.devlib.android.designsystem.R.drawable.ic_home,
        label = "홈",
    ),
    QUESTION(
        route = NavigationRoute.Root.QUESTION,
        iconRes = team.devlib.android.designsystem.R.drawable.ic_question,
        label = "질문",
    ),
    PROFILE(
        route = NavigationRoute.Root.PROFILE,
        iconRes = team.devlib.android.designsystem.R.drawable.ic_profile,
        label = "프로필",
    ),
}
