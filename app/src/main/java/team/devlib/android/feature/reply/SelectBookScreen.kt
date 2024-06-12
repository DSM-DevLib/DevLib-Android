package team.devlib.android.feature.reply

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import team.devlib.android.feature.home.HomeViewModel
import team.devlib.android.feature.home.SearchBar
import team.devlib.android.feature.mypage.BookmarkItem

@Composable
internal fun SelectBookScreen(
    navController: NavController,
    viewModel: HomeViewModel,
) {
    val state by viewModel.state.collectAsState()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.fetchBookRanking()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Color.White)
            .padding(
                top = 24.dp,
                start = 16.dp,
                end = 16.dp,
            ),
    ) {
        SearchBar(
            value = state.keyword,
            onValueChange = viewModel::onKeywordChange,
            hint = "검색어를 입력하세요",
            onClick = {
                viewModel.searchBook()
                focusManager.clearFocus()
            },
        )
        Spacer(modifier = Modifier.height(24.dp))
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            items(viewModel.books) {
                BookmarkItem(
                    id = it.id,
                    onClick = {
                        viewModel.setBookId(bookId = it)
                        navController.popBackStack()
                    },
                    title = it.name,
                    author = it.author,
                    imageUrl = it.cover,
                )
            }
        }
    }
}
