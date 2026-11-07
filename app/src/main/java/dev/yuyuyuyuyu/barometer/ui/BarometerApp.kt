package dev.yuyuyuyuyu.barometer.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import dev.yuyuyuyuyu.barometer.R
import dev.yuyuyuyuyu.barometer.ui.barometer.Barometer
import dev.yuyuyuyuyu.barometer.ui.barometer.BarometerRoute
import dev.yuyuyuyuyu.barometer.ui.openSourceLicenseList.OpenSourceLicenseList
import dev.yuyuyuyuyu.barometer.ui.openSourceLicenseList.OpenSourceLicenseListRoute
import dev.yuyuyuyuyu.barometer.ui.theme.BarometerTheme
import dev.yuyuyuyuyu.simpleTopAppBar.SimpleTopAppBar

@Composable
fun BarometerApp() {
    val backStack = rememberNavBackStack(BarometerRoute)

    val uriHandler = LocalUriHandler.current

    BarometerTheme {
        Scaffold(
            topBar = {
                val currentRoute = backStack.lastOrNull()

                SimpleTopAppBar(
                    title =
                        stringResource(
                            when (currentRoute) {
                                is OpenSourceLicenseListRoute -> R.string.open_source_licenses
                                else -> R.string.app_name
                            },
                        ),
                    navigateBackIsPossible = backStack.size > 1,
                    onNavigateBackButtonClick = { backStack.removeLastOrNull() },
                    onOpenSourceLicensesButtonClick = {
                        if (currentRoute != OpenSourceLicenseListRoute) {
                            backStack.add(OpenSourceLicenseListRoute)
                        }
                    },
                    onSourceCodeButtonClick = {
                        uriHandler.openUri("https://github.com/yuyuyuyuyu-dev/Barometer")
                    },
                )
            },
        ) { innerPadding ->
            NavDisplay(
                backStack = backStack,
                modifier = Modifier.padding(innerPadding),
                onBack = { backStack.removeLastOrNull() },
                entryDecorators =
                    listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator(),
                    ),
                entryProvider =
                    entryProvider {
                        entry<BarometerRoute> { Barometer() }
                        entry<OpenSourceLicenseListRoute> { OpenSourceLicenseList() }
                    },
            )
        }
    }
}
