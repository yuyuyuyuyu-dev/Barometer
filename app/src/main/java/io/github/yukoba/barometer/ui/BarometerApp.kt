package io.github.yukoba.barometer.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import io.github.yukoba.barometer.ui.barometer.BarometerScreen
import io.github.yukoba.barometer.ui.theme.BarometerTheme
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@Composable
fun BarometerApp() {
    val backStack = rememberSaveableBackStack(root = BarometerScreen)
    val navigator = rememberCircuitNavigator(backStack)

    KoinContext {
        BarometerTheme {
            Scaffold { innerPadding ->
                CircuitCompositionLocals(koinInject()) {
                    NavigableCircuitContent(
                        navigator = navigator,
                        backStack = backStack,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}
