package team.devlib.android.feature.review

import android.widget.Toast
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import team.aliens.dms.android.core.designsystem.TextField
import team.devlib.android.R
import team.devlib.android.feature.bookdetails.Header
import team.devlib.designsystem.ui.ButtonDefaults
import team.devlib.designsystem.ui.ContainedButton
import team.devlib.designsystem.ui.DmsTheme

@Composable
internal fun PostReviewScreen(
    navController: NavController,
    bookId: Long,
    viewModel: PostReviewViewModel = hiltViewModel(),
) {
    var review by remember { mutableStateOf("") }
    var star by remember { mutableIntStateOf(0) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                is PostReviewSideEffect.Success -> {
                    Toast.makeText(context, "리뷰가 성공적으로 등록되었습니다!", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                }

                is PostReviewSideEffect.Failure -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F9))
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Header(title = "후기 작성하기") {

        }
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = review,
            onValueChange = { review = it },
            hint = {
                Text(
                    text = "후기를 작성하세요",
                    style = DmsTheme.typography.body2,
                )
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            repeat(5) {
                Icon(
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = { star = it + 1 }
                    ),
                    painter = painterResource(
                        id = if (star - 1 >= it) R.drawable.ic_star_on
                        else R.drawable.ic_star_off,
                    ),
                    contentDescription = null,
                    tint = Color(0xFF999999),
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        ContainedButton(
            modifier = Modifier
                .fillMaxWidth()
                .imePadding()
                .padding(bottom = 56.dp),
            onClick = {
                viewModel.postReview(
                    review = review,
                    bookId = bookId,
                )
            },
            enabled = review.isNotEmpty(),
            colors = ButtonDefaults.buttonColors(containerColor = DmsTheme.colorScheme.surfaceVariant),
        ) {
            Text(
                text = "작성 완료",
                style = DmsTheme.typography.body3,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}
