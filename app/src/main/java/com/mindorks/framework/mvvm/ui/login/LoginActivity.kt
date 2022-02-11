package com.mindorks.framework.mvvm.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.ProvideWindowInsets
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.di.component.ActivityComponent
import com.mindorks.framework.mvvm.ui.base2.BaseActivity
import com.mindorks.framework.mvvm.ui.main.MainActivity
import com.mindorks.framework.mvvm.ui.theme.AndroidmvvmarchitectureTheme

class LoginActivity : BaseActivity<LoginState, LoginViewModel>(), LoginNavigator {

    companion object {
        @JvmStatic
        fun newIntent(context: Context?): Intent? {
            return Intent(context, LoginActivity::class.java)
        }
    }

    override fun handleError(throwable: Throwable?) {
        // handle error
    }

    override fun login(email: String, password: String) {
        if (mViewModel.isEmailAndPasswordValid(email, password)) {
            hideKeyboard()
            mViewModel.login(email, password)
        } else {
            Toast.makeText(this, getString(R.string.invalid_email_password), Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun openMainActivity() {
        val intent = MainActivity.newIntent(this@LoginActivity)
        startActivity(intent)
        finish()
    }

    override fun performDependencyInjection(buildComponent: ActivityComponent?) {
        buildComponent?.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidmvvmarchitectureTheme {
                ProvideWindonwInsets {
                    LoginScreen(
                        { email, password -> login(email, password) },
                        { mViewModel.onGoogleLoginClick() }
                        { mViewModel.onFbLoginClick() }
                    )
                }
            }
        }
    }

}

@Composable
fun LoginScreen(
    // uiState: LoginUiState, // 비즈니스 로직 (뷰 데이터, 로직)
    handleLogin: (email: String?, principal: String?) -> Unit = { _, _ -> },
    handleGoogleLogin: () -> Unit = {},
    handleFbLogin: () -> Unit = {},
    // onBackPress: () -> Unit
) {
    val loginState = rememberLoginState() // UI로직/UI요소 상태 관리 (뷰 보이기/숨김, 열기/닫기 등)
    val email = loginState.email.value.text
    val password = loginState.password.value.text

    val modifier = Modifier.fillMaxWidth()

    Surface(modifier = modifier.fillMaxSize().padding(vertical = 80.dp, horizontal = 16.dp)) {
        Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally, elevation = 22.dp) {
            Logo()
            LoginForm(
                modifier,
                loginState,
                { handleLogin(email, password ) },
            )
            Box(modifier = modifier, contentAlignment = Alignment.BottomCenter) {
                OauthLoginButtons(
                    modifier,
                    { handleGoogleLogin() },
                    { handleFbLogin() },
                )
            }
        }
        // TODO: Show/Hide Loading
        // Box(
        //     modifier = modifier.fillMaxSize().background(color = Color.transparent),
        //     contentAlignment = Alignment.Center
        // ) {
        //     Loading()
        // }
    }
}

@Composable
fun Logo(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        style = MaterialTheme.typography.h2,
        text = stringResource(R.string.login_dummy)
    )
}

@Composable
fun LoginForm(
    modifier: Modifier = Modifier,
    loginState: LoginState,
    onLoginButtonClick: () -> Unit,
    onSignUpButtonClick: () -> Unit
) {
    var passwordVisibility by remember { mutableStateOf(false) }

    Column(modifier.pading(vertical = 20.dp, horizontal = 40.dp)) {
        OutlinedTextField(
            modifier = modifier,
            label = { Text(stringResource(R.string.email)) },
            placeholder = { Text("아이디를 입력해주세요.") },
            value = loginState.email.value,
            onValueChange = { loginState.email.value = it }
        )
        SpacerH(10.dp)
        OutlinedTextField(
            modifier = modifier,
            label = { Text(stringResource(R.string.password)) },
            placeholder = { Text("비밀번호를 입력해주세요.") },
            value = loginState.password.value,
            onValueChange = { loginState.password.value = it },
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisibility)
                        Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(imageVector  = image, "")
                }
            }
        )
        SpacerH(10.dp)
        Box(modifier = modifier.padding(15.dp)) {
            Button(modifier = modifier.padding(10.dp), onClick = { onLoginButtonClick() }) {
                Text("로그인")
            }
        }
    }
}

@Composable
fun OauthLoginButtons(
    modifier: Modifier = Modifier,
    onGoogleLoginButtonClick: () -> Unit,
    onFbLoginButtonClick: () -> Unit,
) {
    Row(modifier = modifier.padding(bottom = 40.dp)) {
        Button(modifier = modifier.size(40.dp), onClick = { onGoogleLoginButtonClick() }) {
            Image(
                modifier = modifier.size(95.dp, 92.dp),
                bitmap = ImageBitmap.imageResource(id = R.drawable.ic_google_plus),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                contentDescription = "google_login"
            )
        }
        SpacerW(24.dp)
        Button(modifier = modifier.size(40.dp), onClick = { onFbLoginButtonClick() }) {
            Image(
                modifier = modifier.size(95.dp, 92.dp),
                bitmap = ImageBitmap.imageResource(id = R.drawable.ic_facebook),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                contentDescription = "facebook_login"
            )
        }
    }
}

@Composable
fun Loading {
    CircularProgressIndicator()
}

@Composable
fun SpacerW(width: Dp) {
    Spacer(modifier = Modifier.width(width = width))
}

@Composable
fun SpacerH(height: Dp) {
    Spacer(modifier = Modifier.height(height = height))
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MaruallyTheme {
        ProvideWindowInsets {
            LoginScreen()
        }
    }
}
