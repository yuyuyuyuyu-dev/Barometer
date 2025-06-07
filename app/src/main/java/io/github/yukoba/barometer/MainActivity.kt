package io.github.yukoba.barometer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import io.github.yukoba.barometer.ui.App
import io.github.yukoba.barometer.ui.theme.BarometerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            BarometerTheme {
                App()
            }
        }
    }
}
