package com.mindorks.framework.mvvm.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.widget.Toolbar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.mindorks.framework.mvvm.di.component.ActivityComponent
import com.mindorks.framework.mvvm.ui.base2.BaseActivity
import com.mindorks.framework.mvvm.ui.login.LoginActivity
import com.mindorks.framework.mvvm.ui.theme.AndroidmvvmarchitectureTheme
import com.mindorks.placeholderview.SwipePlaceHolderView

class MainActivity : BaseActivity<MainState, MainViewModel>(), MainNavigator {

    private var mMainState: MainState? = null
    private var mCardsContainerView: SwipePlaceHolderView? = null
    private var mDrawer: DrawerLayout? = null

    private var mNavigationView: NavigationView? = null
    private var mToolbar: Toolbar? = null

    companion object {
        @JvmStatic
        fun newIntent(context: Context?): Intent? {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun getState(): MainState? {
        return mMainState
    }

    override fun performDependencyInjection(buildComponent: ActivityComponent?) {
        buildComponent?.inject(this)
    }

    override fun handleError(throwable: Throwable?) {
        // handle error
    }

    override fun openLoginActivity() {
        startActivity(LoginActivity.newIntent(this));
        finish();
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: compose view
        setContent {
            AndroidmvvmarchitectureTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidmvvmarchitectureTheme {
        Greeting("Android")
    }
}