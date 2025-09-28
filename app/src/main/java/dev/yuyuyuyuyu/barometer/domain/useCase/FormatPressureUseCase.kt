package dev.yuyuyuyuyu.barometer.domain.useCase

interface FormatPressureUseCase {
    operator fun invoke(pressure: Float): String
}
