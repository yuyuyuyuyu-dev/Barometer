package io.github.yukoba.barometer.ui.types

import androidx.annotation.StringRes
import io.github.yukoba.barometer.R

enum class NavigateDestination(@StringRes val title: Int) {
    Main(title = R.string.app_name),
    ThirdPartyLicenses(title = R.string.third_party_licenses),
    ThirdPartyLicense(title = R.string.third_party_license),
}