package dev.yuyuyuyuyu.barometer.nonSpecification.error

import dev.yuyuyuyuyu.barometer.data.error.BarometricPressureError
import dev.yuyuyuyuyu.barometer.domain.error.DomainError
import dev.yuyuyuyuyu.barometer.error.TraceInfo
import org.junit.Assert.assertEquals
import org.junit.Test

class BaseErrorTest {
    @Test
    fun `should put the newest trace first in the message`() {
        // Arrange
        val error = BarometricPressureError.DeviceDoesNotHaveBarometricSensor()

        // Act
        val actual =
            error
                .appendTrace(TraceInfo(fileName = "Oldest.kt", lineNumber = 1))
                .appendTrace(TraceInfo(fileName = "Newest.kt", lineNumber = 2))
                .messageWithTrace

        // Assert
        assertEquals("device does not have barometric sensor (Newest.kt:2) (Oldest.kt:1)", actual)
    }

    @Test
    fun `should carry the trace of the data layer error into the domain layer`() {
        // Arrange
        val error =
            DomainError.FromDataLayer(
                error =
                    BarometricPressureError
                        .DeviceDoesNotHaveBarometricSensor()
                        .appendTrace(TraceInfo(fileName = "Data.kt", lineNumber = 1)),
            )

        // Act
        val actual = error.appendTrace(TraceInfo(fileName = "Domain.kt", lineNumber = 2)).messageWithTrace

        // Assert
        assertEquals("device does not have barometric sensor (Domain.kt:2) (Data.kt:1)", actual)
    }

    @Test
    fun `should point to the place that asks for the current trace`() {
        // Act
        val actual = TraceInfo.getCurrent()
        val nextLine = Throwable().stackTrace.first()

        // Assert
        assertEquals(TraceInfo(fileName = nextLine.fileName, lineNumber = nextLine.lineNumber - 1), actual)
    }
}
