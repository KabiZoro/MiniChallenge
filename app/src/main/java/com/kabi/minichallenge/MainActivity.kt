package com.kabi.minichallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kabi.minichallenge.sept25DesignTheFest.AccessibilityScreen
import com.kabi.minichallenge.sept25DesignTheFest.MapScreen
import com.kabi.minichallenge.ui.theme.MiniChallengeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniChallengeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = WindowInsets.safeContent
                ) { innerPadding ->
                    /*ExpandableListScreen(
                        modifier = Modifier.padding(innerPadding)
                    )*/
                    /*DynamicUpdateScreen(
                        modifier = Modifier.padding(innerPadding)
                    )*/
                    /*MapScreen(
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(
                                color = Color(0xFFFFFFF0)
                            )
                    )*/
                    AccessibilityScreen(
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

