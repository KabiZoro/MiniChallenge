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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices.TABLET
import androidx.compose.ui.tooling.preview.Preview
import com.kabi.minichallenge.june25BdayCeleb.BirthdayCardScreen
import com.kabi.minichallenge.june25BdayCeleb.CakeLightScreen
import com.kabi.minichallenge.june25BdayCeleb.CandlesLightingScreen
import com.kabi.minichallenge.june25BdayCeleb.GiftMemoryMatchScreen
import com.kabi.minichallenge.june25BdayCeleb.PartyHostDashBoard
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
                    /*AccessibilityScreen(
                        modifier = Modifier
                            .padding(innerPadding)
                    )*/
                    /*BirthdayCardScreen(
                        modifier = Modifier.padding(innerPadding)
                    )*/
                    /*CakeLightScreen(
                        modifier = Modifier.padding(innerPadding)
                    )*/
                    /*GiftMemoryMatchScreen(
                        modifier = Modifier.padding(innerPadding)
                    )*/
                    PartyHostDashBoard(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

