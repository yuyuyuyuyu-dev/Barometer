package io.github.yukoba.barometer.domain.useCases

import io.github.yukoba.barometer.domain.models.GetFormattedBarometricPressureFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeGetFormattedBarometricPressureFlowUseCase : GetFormattedBarometricPressureFlowUseCase {
    override fun invoke(): Flow<String> = flow { emit("149.73100 hPa") }
}
