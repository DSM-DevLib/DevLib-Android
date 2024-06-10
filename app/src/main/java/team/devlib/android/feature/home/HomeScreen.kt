package team.devlib.android.feature.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import team.aliens.dms.android.core.designsystem.shadow
import team.devlib.android.R
import team.devlib.android.feature.mypage.BookmarkItem
import team.devlib.android.navigation.NavigationRoute
import team.devlib.designsystem.ui.ButtonDefaults
import team.devlib.designsystem.ui.DmsTheme
import team.devlib.designsystem.ui.OutlinedButton

@Composable
internal fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val state by homeViewModel.state.collectAsState()
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
            onValueChange = homeViewModel::onKeywordChange,
            hint = "검색어를 입력하세요",
            onClick = {
                homeViewModel.searchBook()
                focusManager.clearFocus()
            },
        )
        Spacer(modifier = Modifier.height(36.dp))
        FilterButtons(
            type = state.type,
            onClick = homeViewModel::onTypeChange,
        )
        Spacer(modifier = Modifier.height(24.dp))
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            items(homeViewModel.books) {
                BookmarkItem(
                    id = it.id,
                    onClick = { navController.navigate("${NavigationRoute.Main.BOOK_DETAILS}/$it") },
                    title = it.name,
                    author = it.author,
                    imageUrl = it.cover,
                )
            }
        }
    }
}

@Composable
internal fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(20.dp),
            )
            .background(Color.White)
            .clip(RoundedCornerShape(20.dp))
            .padding(
                vertical = 8.dp,
                horizontal = 14.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.clickable(
                indication = null,
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
            ),
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = null,
            tint = DmsTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.width(8.dp))
        VerticalDivider(
            modifier = Modifier.height(28.dp),
            color = Color(0xFFEEEEEE),
        )
        BasicTextField(
            modifier = Modifier
                .weight(1f)
                .padding(start = 14.dp),
            value = value,
            onValueChange = onValueChange,
            textStyle = DmsTheme.typography.body3,
            maxLines = 1,
        ) { innerTextField ->
            if (value.isEmpty()) {
                Text(
                    text = hint,
                    style = DmsTheme.typography.body3,
                    color = Color(0xFFDDDDDD),
                    fontWeight = FontWeight.Bold,
                )
            }
            innerTextField()
        }
    }
}

enum class Type(val value: String) {
    VIEW("조회수"),
    REVIEW("후기수"),
    STAR("별점"),
}

@Composable
private fun FilterButtons(
    type: Type?,
    onClick: (Type) -> Unit,
) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        OutlinedButton(
            onClick = { onClick(Type.VIEW) },
            colors = ButtonDefaults.buttonColors(
                containerColor = animateColorAsState(
                    targetValue = if (type == Type.VIEW) Color(0xFF555555)
                    else Color.White,
                    label = "",
                ).value,
            ),
            border = BorderStroke(
                width = ButtonDefaults.OutlineWidth,
                color = Color(0xFF555555),
            ),
        ) {
            Text(
                text = Type.VIEW.value,
                style = DmsTheme.typography.body3,
                color = animateColorAsState(
                    targetValue = if (type == Type.VIEW) Color.White
                    else Color(0xFF555555),
                    label = "",
                ).value,
                fontWeight = FontWeight.Bold,
            )
        }
        OutlinedButton(
            onClick = { onClick(Type.REVIEW) },
            colors = ButtonDefaults.buttonColors(
                containerColor = animateColorAsState(
                    targetValue = if (type == Type.REVIEW) Color(0xFF555555)
                    else Color.White,
                    label = "",
                ).value,
            ),
            border = BorderStroke(
                width = ButtonDefaults.OutlineWidth,
                color = Color(0xFF555555),
            ),
        ) {
            Text(
                text = Type.REVIEW.value,
                style = DmsTheme.typography.body3,
                color = animateColorAsState(
                    targetValue = if (type == Type.REVIEW) Color.White
                    else Color(0xFF555555),
                    label = "",
                ).value,
                fontWeight = FontWeight.Bold,
            )
        }
        OutlinedButton(
            onClick = { onClick(Type.STAR) },
            colors = ButtonDefaults.buttonColors(
                containerColor = animateColorAsState(
                    targetValue = if (type == Type.STAR) Color(0xFF555555)
                    else Color.White,
                    label = "",
                ).value,
            ),
            border = BorderStroke(
                width = ButtonDefaults.OutlineWidth,
                color = Color(0xFF555555),
            ),
        ) {
            Text(
                text = Type.STAR.value,
                style = DmsTheme.typography.body3,
                color = animateColorAsState(
                    targetValue = if (type == Type.STAR) Color.White
                    else Color(0xFF555555),
                    label = "",
                ).value,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Preview
@Composable
private fun SearchBarPreview() {
    DmsTheme {
        SearchBar(
            value = "",
            onValueChange = {},
            hint = "검색어를 입력하세요"
        ) {
        }
    }
}

@Preview
@Composable
private fun FilterButtonsPreview() {
    DmsTheme {
        FilterButtons(type = null) {

        }
    }
}

