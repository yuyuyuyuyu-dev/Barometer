package io.github.yukoba.barometer.domain.useCases

import io.github.yukoba.barometer.data.models.BarometricPressureRepository
import io.github.yukoba.barometer.domain.models.GetFormattedBarometricPressureFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFormattedBarometricPressureFlowUseCaseImpl(
    private val barometricPressureRepository: BarometricPressureRepository,
) : GetFormattedBarometricPressureFlowUseCase {
    // Round to 5 decimal places to match sensor precision.
    override fun invoke(): Flow<String> {
        return barometricPressureRepository.barometricPressure.map { pressure ->
            "%.5f hPa".format(pressure)
        }
    }
}
