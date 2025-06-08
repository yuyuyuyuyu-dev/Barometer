package io.github.yukoba.barometer.domain.models

import com.github.michaelbull.result.Result
import io.github.yukoba.barometer.domain.errors.DomainError
import kotlinx.coroutines.flow.Flow

interface GetFormattedBarometricPressureFlowUseCase {
    operator fun invoke(): Result<Flow<String>, DomainError>
}
