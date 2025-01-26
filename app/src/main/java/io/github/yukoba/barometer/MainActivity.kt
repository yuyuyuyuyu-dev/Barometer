package io.github.yukoba.barometer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import io.github.yukoba.barometer.ui.App
import io.github.yukoba.barometer.ui.features.barometer.BarometerViewModel
import io.github.yukoba.barometer.ui.theme.BarometerTheme

class MainActivity : ComponentActivity() {
    private val barometerViewModel: BarometerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BarometerTheme {
                App()
            }
        }
    }

    override fun onPause() {
        super.onPause()

        barometerViewModel.onPaused()
    }

    override fun onResume() {
        super.onResume()

        barometerViewModel.onResumed()
    }
}