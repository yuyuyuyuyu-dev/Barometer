package io.github.yukoba.barometer.ui

import androidx.annotation.StringRes
import io.github.yukoba.barometer.R

enum class NavigateDestination(@StringRes val title: Int) {
    Main(title = R.string.app_name),
    OpenSourceLicenses(title = R.string.open_source_licenses),
}