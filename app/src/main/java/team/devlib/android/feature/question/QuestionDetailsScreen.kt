package team.devlib.android.feature.question

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import team.aliens.dms.android.core.designsystem.shadow
import team.devlib.android.R
import team.devlib.android.feature.bookdetails.Header
import team.devlib.android.navigation.NavigationRoute
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
        viewModel.sideEffect.collect {
            when (it) {
                QuestionDetailsSideEffect.DeleteSuccess -> {
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
            Column() {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = details.title,
                        style = DmsTheme.typography.body2,
                    )
                    Icon(
                        modifier = Modifier.clickable { viewModel.deleteQuestion() },
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "icon delete",
                    )
                }
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
            Row {
                Text(
                    text = "답변",
                    style = DmsTheme.typography.body2,
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier.clickable { navController.navigate(NavigationRoute.Main.CREATE_REPLY) }
                ) {
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
            Spacer(modifier = Modifier.height(24.dp))
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                repeat(viewModel.replies.size) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(6.dp),
                            )
                            .background(Color.White)
                            .padding(
                                vertical = 18.dp,
                                horizontal = 12.dp,
                            )
                    ) {
                        Row {
                            Text(
                                text = state.replies.username,
                                style = DmsTheme.typography.caption,
                                fontWeight = FontWeight.Bold,
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = state.replies.createdDate.split('T')[0],
                                style = DmsTheme.typography.caption,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        Text(
                            text = state.replies.content,
                            style = DmsTheme.typography.body3,
                        )
                    }
                }
            }
        }
    }
}