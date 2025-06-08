package io.github.yukoba.barometer.domain.useCases

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import io.github.yukoba.barometer.domain.errors.DomainError
import io.github.yukoba.barometer.domain.models.GetFormattedBarometricPressureFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeGetFormattedBarometricPressureFlowUseCase : GetFormattedBarometricPressureFlowUseCase {
    override fun invoke(): Result<Flow<String>, DomainError> = Ok(flow { emit("149.73100 hPa") })
}
