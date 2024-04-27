package team.devlib.android.feature.mypage

import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import team.aliens.dms.android.core.designsystem.shadow
import team.devlib.android.R
import team.devlib.designsystem.ui.DmsTheme

@Composable
internal fun MyPageScreen(
    navController: NavController,
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
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            items(myPageViewModel.bookmarks) {
                BookmarkItem(
                    title = it.name,
                    author = it.author,
                    imageUrl = it.cover,
                    isBookmarked = it.isMarked,
                )
            }
        }
    }
}

@Composable
internal fun BookmarkItem(
    rank: Int? = null,
    title: String,
    author: String,
    imageUrl: String,
    isBookmarked: Boolean? = null,
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
        rank?.run {
            Text(
                modifier = Modifier.padding(end = 8.dp),
                text = this.toString(),
                style = DmsTheme.typography.body3,
            )
        }
        Column {
            Text(
                text = title,
                style = DmsTheme.typography.body2,
            )
            Text(
                text = author,
                style = DmsTheme.typography.overline,
                color = Color(0xFF999999),
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        AsyncImage(
            modifier = Modifier.size(
                width = 36.dp,
                height = 46.dp,
            ),
            model = imageUrl,
            contentDescription = null,
        )
        isBookmarked?.run {
            Spacer(modifier = Modifier.width(20.dp))
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(
                        id = if (isBookmarked) R.drawable.ic_bookmark_on
                        else R.drawable.ic_bookmark_off,
                    ),
                    contentDescription = null,
                    tint = DmsTheme.colorScheme.surfaceVariant,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BookmarkItemPreview() {
    BookmarkItem(
        title = "클린 아키텍처", author = "저자 최하은", imageUrl = "ㅁㅇㄴㄹ", isBookmarked = false
    )
}

@Preview(showBackground = true)
@Composable
private fun MyPageScreenPreview() {
    MyPageScreen(navController = rememberNavController())
}
