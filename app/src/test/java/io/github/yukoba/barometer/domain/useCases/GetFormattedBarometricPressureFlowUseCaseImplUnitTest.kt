package io.github.yukoba.barometer.domain.useCases

import io.github.yukoba.barometer.data.repositories.FakeBarometricPressureRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetFormattedBarometricPressureFlowUseCaseImplUnitTest {
    private lateinit var useCase: GetFormattedBarometricPressureFlowUseCaseImpl

    @Before
    fun setUp() {
        val fakeBarometricPressureRepository = FakeBarometricPressureRepository(
            firstPressure = 149.731f,
        )

        useCase = GetFormattedBarometricPressureFlowUseCaseImpl(
            barometricPressureRepository = fakeBarometricPressureRepository,
        )
    }

    @Test
    fun `invoke() should format pressure from repository to 5 decimal places with hPa`() = runTest {
        val expectedFormattedString = "149.73100 hPa"

        val actualResult = useCase.invoke().first()

        assertEquals(expectedFormattedString, actualResult)
    }
}
