package com.mindorks.framework.mvvm.ui.login

import androidx.compose.runtime.Composable
import com.mindorks.framework.mvvm.ui.base2.BaseState

public class LoginState : BaseState() {

    fun getEmail(): String? {
        // TODO get data from view
        return ""
    }

    fun getPassword(): String? {
        // TODO
        return ""
    }

}

@Composable
public fun rememberLoginState() {
}