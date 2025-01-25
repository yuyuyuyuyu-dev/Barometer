package io.github.yukoba.barometer.ui.features.thirdpartylicenses.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import io.github.yukoba.barometer.ui.features.thirdpartylicenses.types.ThirdPartyLibrary

@Composable
fun ThirdPartyLicensesScreen(
    libraries: List<ThirdPartyLibrary>,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        items(libraries) { library ->
            ListItem(
                headlineContent = { Text(library.name) },
                supportingContent = {
                    Column {
                        Text("")

                        Text("Website")
                        library.website?.let {
                            Text(
                                text = it,
                                color = MaterialTheme.colorScheme.outline,
                                textDecoration = TextDecoration.Underline,
                                modifier = Modifier.clickable {
                                    val intent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(library.website),
                                    )
                                    context.startActivity(intent)
                                },
                            )
                        }

                        Text("")

                        Text("License: ${library.licenseName}")
                        Text(
                            text = library.licenseUrl,
                            color = MaterialTheme.colorScheme.outline,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier.clickable {
                                val intent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(library.licenseUrl),
                                )
                                context.startActivity(intent)
                            },
                        )
                    }
                },
            )

            HorizontalDivider()
        }
    }
}