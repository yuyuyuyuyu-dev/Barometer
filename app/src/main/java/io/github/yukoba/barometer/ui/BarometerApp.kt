package io.github.yukoba.barometer.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import dev.yuyuyuyuyu.simpletopappbar.SimpleTopAppBar
import io.github.yukoba.barometer.R
import io.github.yukoba.barometer.ui.barometer.BarometerScreen
import io.github.yukoba.barometer.ui.openSourceLicensesList.OpenSourceLicenseListScreen
import io.github.yukoba.barometer.ui.theme.BarometerTheme
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@Composable
fun BarometerApp() {
    val backStack = rememberSaveableBackStack(root = BarometerScreen)
    val navigator = rememberCircuitNavigator(backStack)

    KoinContext {
        BarometerTheme {
            Scaffold(
                topBar = {
                    val currentScreen = backStack.topRecord?.screen

                    SimpleTopAppBar(
                        title = stringResource(
                            when (currentScreen) {
                                is OpenSourceLicenseListScreen -> R.string.open_source_licenses
                                else -> R.string.app_name
                            }
                        ),
                        navigateBackIsPossible = backStack.size > 1,
                        onNavigateBackButtonClick = { navigator.pop() },
                        onOpenSourceLicensesButtonClick = {
                            navigator.goTo(OpenSourceLicenseListScreen)
                        },
                    )
                }
            ) { innerPadding ->
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
