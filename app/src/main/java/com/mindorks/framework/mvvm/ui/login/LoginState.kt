package com.mindorks.framework.mvvm.ui.login

import androidx.compose.runtime.Composable
import com.mindorks.framework.mvvm.ui.base2.BaseState

public class LoginState(
    // val scaffoldState: ScaffoldState,
    // val navController: NavHostController,
    private val resources: Resources,
) : BaseState(resources) {
    // ...
}

@Composable
public fun rememberLoginState(
    // scaffoldState: ScaffoldState = rememberScaffoldState(),
    // navController: NavHostController = rememberNavController(),
    resources: Resources = LocalContext.current.resources,
) = remember(
    // scaffoldState,
    // navController,
    resources,
) {
    LoginState(
        // scaffoldState,
        // navController,
        resources,
    )
}
