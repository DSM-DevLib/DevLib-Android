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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
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
                .padding(horizontal = 16.dp),
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = details.title,
                        style = DmsTheme.typography.body2,
                    )
                    if (details.mine) {
                        Icon(
                            modifier = Modifier.clickable { viewModel.deleteQuestion() },
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "icon delete",
                        )
                    }
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
                Row(modifier = Modifier.clickable {
                    navController.navigate("${NavigationRoute.Main.CREATE_REPLY}/$questionId")
                }) {
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
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                items(viewModel.replies) { element ->
                    var like by remember { mutableStateOf(element.liked) }
                    var likeCount by remember { mutableIntStateOf(element.likeCount) }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(6.dp),
                            )
                            .background(Color.White)
                            .padding(
                                vertical = 18.dp,
                                horizontal = 12.dp,
                            ),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = element.username,
                                style = DmsTheme.typography.caption,
                                fontWeight = FontWeight.Bold,
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = element.createdDate.split('T')[0],
                                style = DmsTheme.typography.caption,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = element.content,
                                style = DmsTheme.typography.body3,
                            )
                            AsyncImage(
                                modifier = Modifier.size(
                                    width = 104.dp,
                                    height = 113.dp,
                                ),
                                model = element.imageUrl,
                                contentDescription = null,
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            if (element.mine) {
                                Icon(
                                    modifier = Modifier.clickable {
                                        viewModel.deleteReply(element.id)
                                    },
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "icon delete",
                                )
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    modifier = Modifier.clickable {
                                        like = !like
                                        if (like) {
                                            likeCount += 1
                                            viewModel.deleteGood(element.id)
                                        } else {
                                            likeCount -= 1
                                            viewModel.postGood(element.id)
                                        }
                                    },
                                    painter = painterResource(
                                        id = if (like) R.drawable.ic_fill_good
                                        else R.drawable.ic_empty_good,
                                    ),
                                    contentDescription = "icon good",
                                )
                                Text(
                                    text = likeCount.toString(),
                                    style = DmsTheme.typography.body3,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
