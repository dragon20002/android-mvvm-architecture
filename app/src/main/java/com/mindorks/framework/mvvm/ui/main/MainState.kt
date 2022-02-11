package com.mindorks.framework.mvvm.ui.main

import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.maru_front.ui.base2.BaseState

public class MainState(
    // val scaffoldState: ScaffoldState,
    // val navController: NavHostController,
    private val resources: Resources,
) : BaseState(resources) {
    // ...
}

@Composable
public fun rememberMainState(
    // scaffoldState: ScaffoldState = rememberScaffoldState(),
    // navController: NavHostController = rememberNavController(),
    resources: Resources = LocalContext.current.resources,
) = remember(
    // scaffoldState,
    // navController,
    resources,
) {
    MainState(
        // scaffoldState,
        // navController,
        resources,
    )
}