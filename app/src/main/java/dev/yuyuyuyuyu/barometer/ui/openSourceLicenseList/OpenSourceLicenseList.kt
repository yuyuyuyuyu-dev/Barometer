package dev.yuyuyuyuyu.barometer.ui.openSourceLicenseList

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.mikepenz.aboutlibraries.ui.compose.android.rememberLibraries
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer
import dev.yuyuyuyuyu.barometer.R
import kotlinx.collections.immutable.toImmutableList

@Composable
fun OpenSourceLicenseList(modifier: Modifier = Modifier) {
    val libraries by rememberLibraries(R.raw.aboutlibraries)

    LibrariesContainer(
        libraries = libraries?.libraries?.distinctBy { it.name }?.let {
            libraries?.copy(libraries = it.toImmutableList())
        },
        modifier = modifier,
        showDescription = true,
    )
}
