package io.github.yukoba.barometer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.github.yukoba.barometer.ui.theme.BarometerTheme
import io.github.yukoba.barometer.ui.views.mainview.MainView
import io.github.yukoba.barometer.ui.views.mainview.MainViewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BarometerTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(title = {
                            Text(stringResource(R.string.app_name))
                        })
                    },
                    containerColor = MaterialTheme.colorScheme.background,
                ) { innerPadding ->
                    MainView(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()

        mainViewModel.onPaused()
    }

    override fun onResume() {
        super.onResume()

        mainViewModel.onResumed()
    }
}