package io.github.yukoba.barometer.usecase

import android.content.Context
import io.github.yukoba.barometer.R
import io.github.yukoba.barometer.data.types.Artifact
import io.github.yukoba.barometer.ui.features.thirdpartylicenses.types.ThirdPartyLicense
import kotlinx.serialization.json.Json

class GetThirdPartyLicensesUseCase(
    context: Context,
) {
    private val thirdPartyLicenses: List<ThirdPartyLicense>

    init {
        val inputStream = context.resources.openRawResource(R.raw.artifacts)
        val jsonText = inputStream.bufferedReader().use { it.readText() }
        val artifacts: List<Artifact> = Json.decodeFromString(jsonText)
        thirdPartyLicenses = artifacts.map { artifact ->
            ThirdPartyLicense(
                libraryName = artifact.name,
                website = artifact.scm.url,
                licenseName = artifact.spdxLicenses?.first()?.name
                    ?: artifact.unknownLicenses?.first()?.name ?: "",
                licenseUrl = artifact.spdxLicenses?.first()?.url
                    ?: artifact.unknownLicenses?.first()?.url ?: "",
            )
        }.sortedBy { it.libraryName.lowercase() }
    }

    operator fun invoke() = thirdPartyLicenses
}