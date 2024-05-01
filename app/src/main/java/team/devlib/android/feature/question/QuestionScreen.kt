package team.devlib.android.feature.question

import android.widget.Space
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import team.aliens.dms.android.core.designsystem.shadow
import team.devlib.android.feature.home.SearchBar
import team.devlib.designsystem.ui.DmsTheme

@Composable
internal fun QuestionScreen(
    navController: NavController,
) {
    val (question, onQuestionChange) = remember {
        mutableStateOf("")
    }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 24.dp,
                start = 16.dp,
                end = 16.dp,
            )
            .background(Color.White),
    ) {
        SearchBar(
            value = question,
            onValueChange = onQuestionChange,
            hint = "검색어를 입력하세요.",
            onClick = { focusManager.clearFocus() },
        )
        Spacer(modifier = Modifier.height(40.dp))
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ){

        }
    }
}

@Composable
private fun QuestionItem(
    title: String,
    writer: String,
    date: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
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