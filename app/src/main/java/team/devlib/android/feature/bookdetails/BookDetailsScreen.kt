package team.devlib.android.feature.bookdetails

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import team.aliens.dms.android.core.designsystem.shadow
import team.devlib.android.R
import team.devlib.android.navigation.NavigationRoute
import team.devlib.designsystem.ui.ButtonDefaults
import team.devlib.designsystem.ui.ContainedButton
import team.devlib.designsystem.ui.DmsTheme
import java.text.DecimalFormat

@Composable
internal fun BookDetailsScreen(
    bookId: Long,
    navController: NavController,
    viewModel: BookDetailsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val details = state.details
    var point by remember { mutableIntStateOf(0) }
    val activityResultLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {

        }

    LaunchedEffect(Unit) {
        with(viewModel) {
            setId(bookId)
            fetchBookDetails()
            fetchBookReviews()
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
                .verticalScroll(rememberScrollState())
        ) {

            AsyncImage(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(
                        width = 196.dp,
                        height = 278.dp,
                    ),
                model = details.cover,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(26.dp))
            Row(
                modifier = Modifier.padding(horizontal = 28.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = details.name,
                        style = DmsTheme.typography.body2,
                    )
                    Text(
                        text = details.author,
                        style = DmsTheme.typography.overline,
                        color = Color(0xFF999999),
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = details.description,
                        style = DmsTheme.typography.body3,
                    )
                }
                Icon(
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = viewModel::bookmark,
                        )
                        .align(Alignment.Top),
                    painter = painterResource(
                        id = if (details.isMarked) R.drawable.ic_bookmark_on
                        else R.drawable.ic_bookmark_off
                    ),
                    contentDescription = null,
                )
            }
            Spacer(modifier = Modifier.height(26.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(6.dp),
                    )
                    .background(Color.White)
                    .clip(RoundedCornerShape(6.dp))
                    .padding(
                        horizontal = 12.dp,
                        vertical = 14.dp,
                    )
            ) {
                Column {
                    Text(
                        text = "최저가",
                        style = DmsTheme.typography.overline,
                        color = Color(0xFF999999),
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        modifier = Modifier,
                        text = details.purchaseSite,
                        style = DmsTheme.typography.body3,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = DecimalFormat("#,###").format(details.price).plus("원"),
                        style = DmsTheme.typography.body3,
                        fontWeight = FontWeight.Bold,
                    )
                    ContainedButton(
                        modifier = Modifier.align(Alignment.Bottom),
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(details.purchaseSite))
                            activityResultLauncher.launch(intent)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF555555))
                    ) {
                        Text(
                            text = "바로가기",
                            style = DmsTheme.typography.body3,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(38.dp))
            Row(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = { navController.navigate("${NavigationRoute.Main.POST_REVIEW}/${bookId}") }
                    )
            ) {
                Text(
                    text = "후기",
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
            Spacer(modifier = Modifier.height(28.dp))
            Row(
                modifier = Modifier.padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    repeat(5) {
                        Icon(
                            modifier = Modifier.clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = { point = it + 1 }
                            ),
                            painter = painterResource(
                                id = if (point - 1 >= it) R.drawable.ic_star_on
                                else R.drawable.ic_star_off,
                            ),
                            contentDescription = null,
                            tint = Color(0xFF999999),
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${
                            viewModel.reviews.sumOf { it.score }
                                .div(if (viewModel.reviews.size == 0) 1 else viewModel.reviews.size)
                        }점",
                        style = DmsTheme.typography.body3,
                    )
                    Text(
                        text = "후기 ${viewModel.reviews.size}명",
                        color = Color(0xFF999999)
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier.padding(vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                repeat(viewModel.reviews.size) {
                    val element = viewModel.reviews[it]
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
                                text = element.name,
                                style = DmsTheme.typography.caption,
                                fontWeight = FontWeight.Bold,
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = element.createdAt.split('T')[0],
                                style = DmsTheme.typography.caption,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        Text(
                            text = element.content,
                            style = DmsTheme.typography.body3,
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            repeat(element.score) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_star_on),
                                    contentDescription = null,
                                    tint = Color(0xFF999999),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun Header(
    modifier: Modifier = Modifier,
    title: String? = null,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFF9F9F9))
            .padding(vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick,
            ),
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title ?: "",
            style = DmsTheme.typography.body2,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BookDetailsScreenPreview() {
    DmsTheme {
        BookDetailsScreen(
            bookId = 0L,
            navController = rememberNavController(),
        )
    }
}
