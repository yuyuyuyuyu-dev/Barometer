package io.github.yukoba.barometer.ui.opensourcelicenses.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer

@Composable
fun OpenSourceLicensesScreen(
    modifier: Modifier = Modifier,
) = LibrariesContainer(
    modifier = modifier,
    showDescription = true,
)
