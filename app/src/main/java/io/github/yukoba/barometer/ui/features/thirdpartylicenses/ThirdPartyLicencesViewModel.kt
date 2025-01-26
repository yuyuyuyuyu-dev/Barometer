package io.github.yukoba.barometer.ui.features.thirdpartylicenses

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.github.yukoba.barometer.ui.features.thirdpartylicenses.types.ThirdPartyLicensesUiState
import io.github.yukoba.barometer.usecase.GetThirdPartyLicensesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ThirdPartyLicencesViewModel(application: Application) : AndroidViewModel(application) {
    private val _uiState: MutableStateFlow<ThirdPartyLicensesUiState>
    val uiState: StateFlow<ThirdPartyLicensesUiState>

    private val getThirdPartyLicensesUseCase = GetThirdPartyLicensesUseCase(
        context = getApplication<Application>().applicationContext,
    )

    init {
        val thirdPartyLicenses = getThirdPartyLicensesUseCase()
        _uiState = MutableStateFlow(
            ThirdPartyLicensesUiState(
                thirdPartyLicenses = thirdPartyLicenses,
            )
        )
        uiState = _uiState.asStateFlow()
    }
}