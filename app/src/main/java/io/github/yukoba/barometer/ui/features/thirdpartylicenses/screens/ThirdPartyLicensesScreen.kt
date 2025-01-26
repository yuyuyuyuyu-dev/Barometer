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
import io.github.yukoba.barometer.ui.features.thirdpartylicenses.types.ThirdPartyLicense

@Composable
fun ThirdPartyLicensesScreen(
    thirdPartyLicenses: List<ThirdPartyLicense>,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        items(thirdPartyLicenses) { thirdPartyLicense ->
            ListItem(
                headlineContent = { Text(thirdPartyLicense.libraryName) },
                supportingContent = {
                    Column {
                        Text("")

                        Text("Website")
                        thirdPartyLicense.website?.let {
                            Text(
                                text = it,
                                color = MaterialTheme.colorScheme.outline,
                                textDecoration = TextDecoration.Underline,
                                modifier = Modifier.clickable {
                                    val intent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(it),
                                    )
                                    context.startActivity(intent)
                                },
                            )
                        }

                        Text("")

                        Text("License: ${thirdPartyLicense.licenseName}")
                        Text(
                            text = thirdPartyLicense.licenseUrl,
                            color = MaterialTheme.colorScheme.outline,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier.clickable {
                                val intent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(thirdPartyLicense.licenseUrl),
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