package com.abraham.toolboxchanllenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import com.abraham.toolboxchanllenge.ui.navigation.NavGraph
import com.abraham.toolboxchanllenge.ui.theme.MyComposeAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeAppTheme {
                NavGraph()
            }
        }
    }
}
