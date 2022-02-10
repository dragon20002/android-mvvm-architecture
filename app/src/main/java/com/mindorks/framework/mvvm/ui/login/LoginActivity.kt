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

    private var mLoginState: LoginState? = null

    companion object {
        @JvmStatic
        fun newIntent(context: Context?): Intent? {
            return Intent(context, LoginActivity::class.java)
        }
    }

    override fun getState(): LoginState? {
        return mLoginState
    }

    override fun handleError(throwable: Throwable?) {
        // handle error
    }

    override fun login() {
        val email = mLoginState.getEmail()
        val password = mLoginState.getPassword()
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
        // TODO: compose view
        setContent {
            AndroidmvvmarchitectureTheme {
                ProvideWindonwInsets {
                    LoginScreen(viewModel.uiState)
                }
            }
        }
    }

}

@Composable
fun LoginScreen(
    uiState: LoginUiState, // 비즈니스 로직 (뷰 데이터, 로직)
    // onBackPress: () -> Unit
) {
    val loginState = rememberLoginState() // UI로직/UI요소 상태 관리 (뷰 보이기/숨김, 열기/닫기 등)

    // ...
    Box(modifier = Modifier.fillMaxSize()) {

    }
}

@Composable
fun Greeting2(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    AndroidmvvmarchitectureTheme {
        val LoginState = rememberLoginState()
        LoginScreen(LoginState)
    }
}
