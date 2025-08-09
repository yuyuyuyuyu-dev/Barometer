package dev.yuyuyuyuyu.barometer.domain.useCases

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.fold
import dev.yuyuyuyuyu.barometer.data.models.BarometricPressureRepository
import dev.yuyuyuyuyu.barometer.domain.errors.DomainError
import dev.yuyuyuyuyu.barometer.domain.models.GetFormattedBarometricPressureFlowUseCase
import dev.yuyuyuyuyu.barometer.error.TraceInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFormattedBarometricPressureFlowUseCaseImpl(
    private val barometricPressureRepository: BarometricPressureRepository,
) : GetFormattedBarometricPressureFlowUseCase {
    // Round to 5 decimal places to match sensor precision.
    override fun invoke(): Result<Flow<String>, DomainError> {
        val result = barometricPressureRepository.getBarometricPressureFlow()
        return result.fold(
            success = { pressure ->
                Ok(pressure.map { "%.5f hPa".format(it) })
            },
            failure = { error ->
                Err(DomainError.FromDataLayer(error).appendTrace(TraceInfo.getCurrent()))
            },
        )
    }
}
