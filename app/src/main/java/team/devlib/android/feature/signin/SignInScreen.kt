package team.devlib.android.feature.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import team.aliens.dms.android.core.designsystem.TextField
import team.devlib.android.R
import team.devlib.designsystem.ui.ButtonDefaults
import team.devlib.designsystem.ui.ContainedButton
import team.devlib.designsystem.ui.DmsTheme

@Composable
internal fun SignInScreen(navController: NavController) {
    val (email, onEmailChange) = remember { mutableStateOf("") }
    val (password, onPasswordChange) = remember { mutableStateOf("") }
    val (visible, setVisible) = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Column(modifier = Modifier.padding(top = 128.dp)) {
            Image(
                modifier = Modifier
                    .size(
                        width = 68.dp,
                        height = 24.dp,
                    )
                    .padding(start = 6.dp),
                painter = painterResource(id = R.drawable.ic_devlib),
                contentDescription = null,
            )
            Text(
                text = "로그인",
                style = DmsTheme.typography.headline1.copy(fontSize = 32.sp),
                fontWeight = FontWeight.Bold,
                color = Color(0xFF555555),
            )
        }
        Spacer(modifier = Modifier.height(60.dp))
        TextField(
            value = email,
            onValueChange = onEmailChange,
            hint = {
                Text(
                    text = "아이디",
                    style = DmsTheme.typography.body2,
                )
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
        TextField(
            value = password,
            onValueChange = onPasswordChange,
            hint = {
                Text(
                    text = "비밀번호",
                    style = DmsTheme.typography.body2,
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = { setVisible(!visible) },
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (visible) team.devlib.android.designsystem.R.drawable.ic_password_visible
                            else team.devlib.android.designsystem.R.drawable.ic_password_invisible,
                        ),
                        contentDescription = null,
                    )
                }
            },
            visualTransformation = if (visible) VisualTransformation.None
            else PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.weight(1f))
        ContainedButton(
            modifier = Modifier
                .fillMaxWidth()
                .imePadding()
                .padding(bottom = 56.dp),
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(containerColor = DmsTheme.colorScheme.surfaceVariant),
        ) {
            Text(
                text = "로그인",
                style = DmsTheme.typography.body3,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignInPreview() {
    DmsTheme {
        SignInScreen(navController = rememberNavController())
    }
}
