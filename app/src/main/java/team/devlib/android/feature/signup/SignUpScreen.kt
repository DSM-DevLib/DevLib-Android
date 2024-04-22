package team.devlib.android.feature.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import team.aliens.dms.android.core.designsystem.LocalToast
import team.aliens.dms.android.core.designsystem.TextField
import team.devlib.android.navigation.NavigationRoute
import team.devlib.android.R
import team.devlib.designsystem.ui.ButtonDefaults
import team.devlib.designsystem.ui.ContainedButton
import team.devlib.designsystem.ui.DmsTheme

@Composable
internal fun SignUpScreen(
    navController: NavController,
    signUpViewModel: SignUpViewModel = hiltViewModel(),
) {
    val toast = LocalToast.current
    val state by signUpViewModel.state.collectAsState()
    val (visible, setVisible) = remember { mutableStateOf(false) }
    val (passwordRepeatVisible, setPasswordRepeatVisible) = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        signUpViewModel.sideEffect.collect {
            when (it) {
                is SignUpSideEffect.Success -> {
                    withContext(Dispatchers.Main) {
                        navController.navigate(NavigationRoute.Main.MAIN) {
                            popUpTo(0)
                        }
                    }
                    toast.showSuccessToast(message = it.message)
                }

                is SignUpSideEffect.Failure -> {
                    toast.showErrorToast(message = it.message)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
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
                text = "회원가입",
                style = DmsTheme.typography.headline1.copy(fontSize = 32.sp),
                fontWeight = FontWeight.Bold,
                color = Color(0xFF555555),
            )
        }
        Spacer(modifier = Modifier.height(60.dp))
        TextField(
            value = state.accountId,
            onValueChange = signUpViewModel::setAccountId,
            hint = {
                Text(
                    text = "이메일",
                    style = DmsTheme.typography.body2,
                )
            },
            isError = state.emailError,
            supportingText = {
                if (state.emailError) {
                    Text(
                        text = "존재하지 않는 이메일입니다.",
                        style = DmsTheme.typography.body3,
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
        TextField(
            value = state.password,
            onValueChange = signUpViewModel::setPassword,
            hint = {
                Text(
                    text = "비밀번호",
                    style = DmsTheme.typography.body2,
                )
            },
            isError = state.passwordError,
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
            else PasswordVisualTransformation(),
            supportingText = {
                if (state.passwordError) {
                    Text(
                        text = "잘못된 비밀번호입니다.",
                        style = DmsTheme.typography.body3,
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
        TextField(
            value = state.repeatPassword,
            onValueChange = signUpViewModel::setRepeatPassword,
            hint = {
                Text(
                    text = "비밀번호 확인",
                    style = DmsTheme.typography.body2,
                )
            },
            isError = state.repeatPasswordError,
            trailingIcon = {
                IconButton(
                    onClick = { setPasswordRepeatVisible(!passwordRepeatVisible) },
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (passwordRepeatVisible) team.devlib.android.designsystem.R.drawable.ic_password_visible
                            else team.devlib.android.designsystem.R.drawable.ic_password_invisible,
                        ),
                        contentDescription = null,
                    )
                }
            },
            visualTransformation = if (passwordRepeatVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            supportingText = {
                if (state.repeatPasswordError) {
                    Text(
                        text = "비밀번호가 일치하지 않습니다.",
                        style = DmsTheme.typography.body3,
                    )
                }
            }
        )
        Text(
            modifier = Modifier
                .align(Alignment.End)
                .padding(12.dp)
                .clickable {
                    navController.navigate(NavigationRoute.Auth.SIGN_IN) {
                        popUpTo(0)
                    }
                },
            text = "로그인",
            style = DmsTheme.typography.caption,
            color = DmsTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.weight(1f))
        ContainedButton(
            modifier = Modifier
                .fillMaxWidth()
                .imePadding()
                .padding(bottom = 56.dp),
            onClick = signUpViewModel::signUp,
            colors = ButtonDefaults.buttonColors(containerColor = DmsTheme.colorScheme.surfaceVariant),
        ) {
            Text(
                text = "회원가입",
                style = DmsTheme.typography.body3,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpPreview() {
    DmsTheme {
        SignUpScreen(navController = rememberNavController())
    }
}
