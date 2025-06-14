package dev.yuyuyuyuyu.barometer.domain.useCases

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import dev.yuyuyuyuyu.barometer.domain.errors.DomainError
import dev.yuyuyuyuyu.barometer.domain.models.GetFormattedBarometricPressureFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeGetFormattedBarometricPressureFlowUseCase : GetFormattedBarometricPressureFlowUseCase {
    override fun invoke(): Result<Flow<String>, DomainError> = Ok(flow { emit("149.73100 hPa") })
}
