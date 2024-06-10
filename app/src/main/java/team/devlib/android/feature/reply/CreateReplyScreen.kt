package team.devlib.android.feature.reply

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import team.aliens.dms.android.core.designsystem.TextField
import team.devlib.android.feature.bookdetails.Header
import team.devlib.android.feature.home.HomeSideEffect
import team.devlib.android.feature.home.HomeViewModel
import team.devlib.android.navigation.NavigationRoute
import team.devlib.designsystem.ui.ButtonDefaults
import team.devlib.designsystem.ui.ContainedButton
import team.devlib.designsystem.ui.DmsTheme

@Composable
internal fun CreateReplyScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                HomeSideEffect.CreateReplySuccess -> {
                    navController.navigate(NavigationRoute.Main.MAIN)
                }
            }
        }

    }

    Column {
        Header(
            modifier = Modifier.padding(horizontal = 16.dp),
            onClick = navController::popBackStack,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            TextField(
                value = state.content,
                onValueChange = viewModel::onContentChange,
                hint = {
                    Text(
                        text = "답변을 입력하세요",
                        style = DmsTheme.typography.body2,
                    )
                },
                maxLines = 30,
                singleLine = false,
            )
            Spacer(modifier = Modifier.height(24.dp))
            ContainedButton(
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(containerColor = DmsTheme.colorScheme.surfaceVariant),
                onClick = { navController.navigate(NavigationRoute.Main.SELECT_BOOK) },
            ) {
                Text(
                    text = "책 선택하기",
                    style = DmsTheme.typography.button,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            ContainedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DmsTheme.colorScheme.surfaceVariant),
                onClick = { viewModel.postReply() }
            ) {
                Text(
                    text = "선택하기",
                    style = DmsTheme.typography.button,
                )
            }

        }
    }
}