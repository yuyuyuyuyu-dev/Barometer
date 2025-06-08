package dev.yuyuyuyuyu.barometer.domain.models

import com.github.michaelbull.result.Result
import dev.yuyuyuyuyu.barometer.domain.errors.DomainError
import kotlinx.coroutines.flow.Flow

interface GetFormattedBarometricPressureFlowUseCase {
    operator fun invoke(): Result<Flow<String>, DomainError>
}
