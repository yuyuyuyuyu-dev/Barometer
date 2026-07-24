package dev.yuyuyuyuyu.barometer.ui.openSourceLicenseList

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.mikepenz.aboutlibraries.ui.compose.android.produceLibraries
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer
import com.mikepenz.aboutlibraries.ui.compose.variant.LibraryBadges
import dev.yuyuyuyuyu.barometer.R

@Composable
fun OpenSourceLicenseList(modifier: Modifier = Modifier) {
    val libraries by produceLibraries(R.raw.aboutlibraries)

    LibrariesContainer(
        libraries =
            libraries?.libraries?.distinctBy { it.name }?.let {
                libraries?.copy(libraries = it)
            },
        modifier = modifier,
        // AboutLibraries 15 replaced the individual show* flags with a badge set,
        // whose defaults show everything here except the description.
        badges = LibraryBadges(description = true),
    )
}
