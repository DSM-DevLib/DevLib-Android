package team.devlib.android.feature.question

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import team.aliens.dms.android.core.designsystem.TextField
import team.devlib.android.feature.bookdetails.Header
import team.devlib.android.navigation.NavigationRoute
import team.devlib.designsystem.ui.ButtonDefaults
import team.devlib.designsystem.ui.ContainedButton
import team.devlib.designsystem.ui.DmsTheme

@Composable
internal fun CreateQuestionScreen(
    navController: NavController,
    viewModel: QuestionViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when(it) {
                QuestionSideEffect.Success -> {
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
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            TextField(
                value = state.title,
                onValueChange = viewModel::setTitle,
                hint = {
                    Text(
                        text = "제목을 입력하세요",
                        style = DmsTheme.typography.body2,
                    )
                },
            )
            Spacer(modifier = Modifier.height(14.dp))
            TextField(
                modifier = Modifier
                    .fillMaxHeight(0.5f),
                value = state.content,
                onValueChange = viewModel::setContent,
                hint = {
                    Text(
                        text = "내용을 입력하세요",
                        style = DmsTheme.typography.body2,
                    )
                },
                singleLine = false,
                maxLines = 2000,
            )
            Spacer(modifier = Modifier.weight(1f))
            ContainedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding()
                    .padding(bottom = 56.dp),
                onClick = { viewModel.postQuestion() },
                colors = ButtonDefaults.buttonColors(containerColor = DmsTheme.colorScheme.surfaceVariant),
            ) {
                Text(
                    text = "작성 완료",
                    style = DmsTheme.typography.body3,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}