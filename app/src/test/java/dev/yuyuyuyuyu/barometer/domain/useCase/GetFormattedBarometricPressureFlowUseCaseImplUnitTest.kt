package dev.yuyuyuyuyu.barometer.domain.useCase

import app.cash.turbine.test
import com.github.michaelbull.result.unwrap
import dev.yuyuyuyuyu.barometer.data.repository.fake.FakeBarometricPressureRepository
import dev.yuyuyuyuyu.barometer.domain.useCase.impl.GetFormattedBarometricPressureFlowUseCaseImpl
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetFormattedBarometricPressureFlowUseCaseImplUnitTest {
    private lateinit var useCase: GetFormattedBarometricPressureFlowUseCaseImpl
    private lateinit var fakeBarometricPressureRepository: FakeBarometricPressureRepository

    @Before
    fun setUp() {
        fakeBarometricPressureRepository = FakeBarometricPressureRepository(
            firstPressure = 149.731f,
        )

        useCase = GetFormattedBarometricPressureFlowUseCaseImpl(
            barometricPressureRepository = fakeBarometricPressureRepository,
        )
    }

    @Test
    fun `invoke() should format pressure from repository to 5 decimal places with hPa`() = runTest {
        val expectedFormattedString = "149.73100 hPa"

        val actualResult = useCase.invoke().unwrap().first()

        assertEquals(expectedFormattedString, actualResult)
    }

    @Test
    fun `invoke() should emit formatted pressures when repository values change`() = runTest {
        useCase.invoke().unwrap().test {
            assertEquals("149.73100 hPa", awaitItem()) // Assert initial value

            fakeBarometricPressureRepository.updatePressure(7f)
            assertEquals("7.00000 hPa", awaitItem())

            fakeBarometricPressureRepository.updatePressure(31f)
            assertEquals("31.00000 hPa", awaitItem())

            fakeBarometricPressureRepository.updatePressure(12f)
            assertEquals("12.00000 hPa", awaitItem())

            fakeBarometricPressureRepository.updatePressure(149f)
            assertEquals("149.00000 hPa", awaitItem())

            cancelAndConsumeRemainingEvents()
        }
    }
}
