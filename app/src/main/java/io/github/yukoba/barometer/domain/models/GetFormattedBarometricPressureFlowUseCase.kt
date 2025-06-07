package io.github.yukoba.barometer.domain.models

import kotlinx.coroutines.flow.Flow

interface GetFormattedBarometricPressureFlowUseCase {
    operator fun invoke() : Flow<String>
}
