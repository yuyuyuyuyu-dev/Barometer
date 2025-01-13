package io.github.yukoba.barometer.ui.features.thirdpartylicenses.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ThirdPartyLicensesScreen(
    onLicenseCardClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val l = listOf(
        "CreateTypographyFromFontName",
        "Jetpack Compose",
    )

    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        items(l) {
            ElevatedCard(
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        Text(
                            text = it,
                            textAlign = TextAlign.Center,
                        )
                    }
                },
                onClick = {
                    onLicenseCardClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(68.dp)
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 10.dp),
            )
        }
    }
}