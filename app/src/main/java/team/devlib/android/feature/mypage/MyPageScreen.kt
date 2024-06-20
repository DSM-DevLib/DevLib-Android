package team.devlib.android.feature.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import team.aliens.dms.android.core.designsystem.shadow
import team.devlib.android.navigation.NavigationRoute
import team.devlib.designsystem.ui.DmsTheme

@Composable
internal fun MyPageScreen(
    navHostController: NavController,
    myPageViewModel: MyPageViewModel = hiltViewModel(),
) {
    val state by myPageViewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 42.dp,
                    start = 16.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape),
                model = "https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg",
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.width(18.dp))
            Text(
                text = state.name,
                style = DmsTheme.typography.body2,
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            modifier = Modifier.padding(start = 26.dp),
            text = "북마크",
            style = DmsTheme.typography.body2
        )
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            items(myPageViewModel.bookmarks) {
                BookmarkItem(
                    id = it.id,
                    onClick = { navHostController.navigate("${NavigationRoute.Main.BOOK_DETAILS}/$it") },
                    title = it.name,
                    author = it.author,
                    imageUrl = it.cover,
                )
            }
        }
    }
}

@Composable
internal fun BookmarkItem(
    onClick: (bookId: Long) -> Unit,
    id: Long,
    rank: Int? = null,
    title: String,
    author: String,
    imageUrl: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(6.dp),
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onClick(id) }
            )
            .background(Color.White)
            .padding(
                horizontal = 18.dp,
                vertical = 8.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        rank?.run {
            Text(
                modifier = Modifier.padding(end = 8.dp),
                text = this.toString(),
                style = DmsTheme.typography.body3,
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = DmsTheme.typography.body2,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
            Text(
                text = author,
                style = DmsTheme.typography.overline,
                color = Color(0xFF999999),
            )
        }
        AsyncImage(
            modifier = Modifier.size(
                width = 36.dp,
                height = 46.dp,
            ),
            model = imageUrl,
            contentDescription = null,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BookmarkItemPreview() {
    BookmarkItem(
        title = "클린 아키텍처",
        author = "저자 최하은",
        imageUrl = "ㅁㅇㄴㄹ",
        onClick = {},
        id = 0L,
    )
}

@Preview(showBackground = true)
@Composable
private fun MyPageScreenPreview() {
    MyPageScreen(navHostController = rememberNavController())
}
