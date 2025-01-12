package io.github.yukoba.barometer.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import io.github.yukoba.barometer.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    title: String,
    navigateBackIsPossible: Boolean,
    onNavigateBackButtonClick: () -> Unit,
    onNavigateThirdPartyLicensesButtonClick: () -> Unit,
) = androidx.compose.material3.TopAppBar(
    title = { Text(title) },
    navigationIcon = {
        if (navigateBackIsPossible) {
            IconButton(
                content = {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "back")
                },
                onClick = onNavigateBackButtonClick,
            )
        }
    },
    actions = {
        var menuIsExpanded by rememberSaveable { mutableStateOf(false) }

        IconButton(
            content = {
                Icon(Icons.Default.MoreVert, "menu")
            },
            onClick = {
                menuIsExpanded = true
            },
        )

        DropdownMenu(
            expanded = menuIsExpanded,
            onDismissRequest = { menuIsExpanded = false },
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(R.string.third_party_licenses)) },
                onClick = {
                    onNavigateThirdPartyLicensesButtonClick()
                    menuIsExpanded = false
                },
            )
        }
    }
)