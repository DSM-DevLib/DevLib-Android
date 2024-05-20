package team.devlib.android.feature.question

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import team.devlib.android.R
import team.devlib.android.feature.bookdetails.Header
import team.devlib.designsystem.ui.DmsTheme

@Composable
internal fun QuestionDetailsScreen(
    navController: NavController,
    questionId: Long,
    viewModel: QuestionDetailsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val details = state.details

    LaunchedEffect(Unit) {
        viewModel.setId(questionId)
        viewModel.fetchQuestionDetails()
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
                .verticalScroll(rememberScrollState()),
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = details.title,
                    style = DmsTheme.typography.body2,
                )
                Text(
                    text = details.author,
                    style = DmsTheme.typography.overline,
                    color = Color(0xFF999999),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = details.content,
                    style = DmsTheme.typography.body3,
                )
            }
            Spacer(modifier = Modifier.height(38.dp))
            Row(modifier = Modifier.padding(horizontal = 24.dp)) {
                Text(
                    text = "답변",
                    style = DmsTheme.typography.body2,
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "작성하기",
                    style = DmsTheme.typography.body2,
                    color = Color(0xFF999999),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_pencil),
                    contentDescription = null,
                )
            }
        }
    }
}