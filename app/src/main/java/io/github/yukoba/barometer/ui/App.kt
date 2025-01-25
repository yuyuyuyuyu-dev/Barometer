package io.github.yukoba.barometer.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.yukoba.barometer.ui.components.TopAppBar
import io.github.yukoba.barometer.ui.features.barometer.BarometerViewModel
import io.github.yukoba.barometer.ui.features.barometer.screens.BarometerScreen
import io.github.yukoba.barometer.ui.features.thirdpartylicenses.screens.ThirdPartyLicenseScreen
import io.github.yukoba.barometer.ui.features.thirdpartylicenses.screens.ThirdPartyLicensesScreen
import io.github.yukoba.barometer.ui.features.thirdpartylicenses.types.ThirdPartyLibrary
import io.github.yukoba.barometer.ui.types.NavigateDestination

@Composable
fun App(
    navController: NavHostController = rememberNavController(),
    barometerViewModel: BarometerViewModel = viewModel(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = NavigateDestination.valueOf(
        backStackEntry?.destination?.route ?: NavigateDestination.Main.name
    )

    val barometerUiState by barometerViewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = stringResource(currentScreen.title),
                navigateBackIsPossible = navController.previousBackStackEntry != null,
                onNavigateBackButtonClick = {
                    navController.navigateUp()
                },
                onNavigateThirdPartyLicensesButtonClick = {
                    if (currentScreen != NavigateDestination.ThirdPartyLicenses) {
                        navController.navigate(NavigateDestination.ThirdPartyLicenses.name)
                    }
                },
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigateDestination.Main.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = NavigateDestination.Main.name) {
                BarometerScreen(barometricPressure = barometerUiState.barometricPressure)
            }

            composable(route = NavigateDestination.ThirdPartyLicenses.name) {
                ThirdPartyLicensesScreen(
                    libraries = listOf(
                        ThirdPartyLibrary(
                            name = "CreateTypographyFromFontName",
                            website = "git://github.com/yu-ko-ba/CreateTypographyFromFontName.git",
                            licenseName = "MIT",
                            licenseUrl = "https://github.com/yu-ko-ba/CreateTypographyFromFontName/blob/main/LICENSE",
                        )
                    ),
                )
            }

            composable(route = NavigateDestination.ThirdPartyLicense.name) {
                ThirdPartyLicenseScreen()
            }
        }
    }
}