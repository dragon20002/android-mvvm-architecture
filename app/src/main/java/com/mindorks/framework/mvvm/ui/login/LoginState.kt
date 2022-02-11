package com.mindorks.framework.mvvm.ui.login

import androidx.compose.runtime.Composable
import com.mindorks.framework.mvvm.ui.base2.BaseState

public class LoginState(
    private val resources: Resources,
    var email: MutableState<TextFieldValue>,
    var password: MutableState<TextFieldValue>,
) : BaseState(resources)

@Composable
public fun rememberLoginState(
    resources: Resources = LocalContext.current.resources,
    email: MutableState<TextFieldValue> = remember { mutableStateOf(TextFieldValue("")) },
    password: MutableState<TextFieldValue> = remember { mutableStateOf(TextFieldValue("")) },
) = remember(resources, email, password) {
    LoginState(resources, email, password)
}
