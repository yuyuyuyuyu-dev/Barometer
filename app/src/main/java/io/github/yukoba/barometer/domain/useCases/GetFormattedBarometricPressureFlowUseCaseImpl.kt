package io.github.yukoba.barometer.domain.useCases

import io.github.yukoba.barometer.data.models.BarometricPressureRepository
import io.github.yukoba.barometer.domain.models.GetFormattedBarometricPressureFlowUseCase
import kotlinx.coroutines.flow.Flow

class GetFormattedBarometricPressureFlowUseCaseImpl(
    private val barometricPressureRepository: BarometricPressureRepository,
) : GetFormattedBarometricPressureFlowUseCase {
    override fun invoke(): Flow<String> {
        // Round to 5 decimal places to match sensor precision.

        TODO("Not yet implemented")
    }
}
