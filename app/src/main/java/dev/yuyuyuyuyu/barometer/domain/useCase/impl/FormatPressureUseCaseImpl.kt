package dev.yuyuyuyuyu.barometer.domain.useCase.impl

import dev.yuyuyuyuyu.barometer.domain.useCase.FormatPressureUseCase

class FormatPressureUseCaseImpl : FormatPressureUseCase {
    // Round to 5 decimal places to match sensor precision.
    override fun invoke(pressure: Float) = "%.5f hPa".format(pressure)
}
