package team.devlib.android.feature.question

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import team.aliens.dms.android.core.designsystem.shadow
import team.devlib.android.R
import team.devlib.android.feature.home.SearchBar
import team.devlib.android.navigation.NavigationRoute
import team.devlib.designsystem.ui.DmsTheme

@Composable
internal fun QuestionScreen(
    navController: NavController,
    navHostController: NavHostController,
    questionViewModel: QuestionViewModel = hiltViewModel(),
) {
    val state by questionViewModel.state.collectAsState()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(
                top = 24.dp,
                start = 16.dp,
                end = 16.dp,
            ),
    ) {
        SearchBar(
            value = state.keyword,
            onValueChange = questionViewModel::onKeywordChange,
            hint = "검색어를 입력하세요.",
            onClick = {
                questionViewModel.fetchQuestions()
                focusManager.clearFocus()
            },
        )
        Spacer(modifier = Modifier.height(40.dp))
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            items(questionViewModel.questions) {
                QuestionItem(
                    questionId = it.questionId,
                    title = it.title,
                    writer = it.username,
                    date = it.createdDate.split('T')[0],
                    onClick = { navController.navigate("${NavigationRoute.Main.QUESTION_DETAILS}/$it") }
                )
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.End)
                .padding(bottom = 24.dp),
            onClick = { navController.navigate(NavigationRoute.Main.CREATE_QUESTION) },
            containerColor = Color.Black,
            contentColor = Color.White,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = "icon plus",
            )
        }
    }
}

@Composable
private fun QuestionItem(
    questionId: Long,
    title: String,
    writer: String,
    date: String,
    onClick: (questionId: Long) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(questionId) }
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(6.dp),
            )
            .background(Color.White)
            .padding(
                horizontal = 18.dp,
                vertical = 8.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text(
                text = title,
                style = DmsTheme.typography.body2,
            )
            Text(
                text = writer,
                style = DmsTheme.typography.overline,
                color = Color(0xFF999999),
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = date,
                style = DmsTheme.typography.overline,
                color = Color(0xFF999999),
            )
        }
    }
}