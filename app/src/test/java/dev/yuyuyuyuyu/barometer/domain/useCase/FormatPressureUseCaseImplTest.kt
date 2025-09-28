package dev.yuyuyuyuyu.barometer.domain.useCase

import dev.yuyuyuyuyu.barometer.domain.useCase.impl.FormatPressureUseCaseImpl
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class FormatPressureUseCaseImplTest {
    private val useCase = FormatPressureUseCaseImpl()

    @Test
    fun `invoke() should format pressure to 5 decimal places with hPa`() = runTest {
        // Arrange
        val expected = "149.73100 hPa"

        // Act
        val actual = useCase(pressure = 149.731f)

        // Assert
        assertEquals(expected, actual)
    }
}
