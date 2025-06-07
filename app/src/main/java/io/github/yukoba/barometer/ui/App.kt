package io.github.yukoba.barometer.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import dev.yuyuyuyuyu.simpletopappbar.SimpleTopAppBar
import io.github.yukoba.barometer.R
import io.github.yukoba.barometer.ui.barometer.BarometerViewModel
import io.github.yukoba.barometer.ui.barometer.screens.BarometerScreen
import io.github.yukoba.barometer.ui.opensourcelicenses.screens.OpenSourceLicensesScreen

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
            SimpleTopAppBar(
                title = stringResource(currentScreen.title),
                navigateBackIsPossible = navController.previousBackStackEntry != null,
                openSourceLicensesButtonLabel = { Text(stringResource(R.string.open_source_licenses)) },
                onNavigateBackButtonClick = {
                    navController.navigateUp()
                },
                onOpenSourceLicensesButtonClick = {
                    if (currentScreen != NavigateDestination.OpenSourceLicenses) {
                        navController.navigate(NavigateDestination.OpenSourceLicenses.name)
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
                BarometerScreen(barometerUiState)
            }

            composable(route = NavigateDestination.OpenSourceLicenses.name) {
                OpenSourceLicensesScreen()
            }
        }
    }
}
