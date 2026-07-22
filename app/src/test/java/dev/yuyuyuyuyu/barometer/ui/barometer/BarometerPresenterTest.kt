package dev.yuyuyuyuyu.barometer.ui.barometer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.testing.TestLifecycleOwner
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.slack.circuit.test.CircuitReceiveTurbine
import com.slack.circuit.test.presenterTestOf
import dev.yuyuyuyuyu.barometer.data.dataSource.fake.FakeBarometricPressureDataSource
import dev.yuyuyuyuyu.barometer.data.error.BarometricPressureError
import dev.yuyuyuyuyu.barometer.data.repository.impl.BarometricPressureRepositoryImpl
import dev.yuyuyuyuyu.barometer.domain.useCase.impl.FormatPressureUseCaseImpl
import dev.yuyuyuyuyu.barometer.testRule.MainDispatcherRule
import dev.yuyuyuyuyu.barometer.ui.barometer.model.BarometerState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class BarometerPresenterTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val dataSource = FakeBarometricPressureDataSource()

    @Test
    fun `should start in the loading state before the sensor reports anything`() =
        runTest {
            testPresenter {
                assertEquals(BarometerState.Loading, awaitItem().barometerState)
            }
        }

    @Test
    fun `should show the formatted pressure once the sensor reports a reading`() =
        runTest {
            testPresenter {
                assertEquals(BarometerState.Loading, awaitItem().barometerState)

                dataSource.emit(Ok(1013.25f))

                assertEquals(
                    BarometerState.SuccessToGetPressure(pressure = "1013.25000 hPa"),
                    awaitItem().barometerState,
                )
            }
        }

    @Test
    fun `should follow the sensor when it reports a new reading`() =
        runTest {
            testPresenter {
                assertEquals(BarometerState.Loading, awaitItem().barometerState)

                dataSource.emit(Ok(1013.25f))
                assertEquals(
                    BarometerState.SuccessToGetPressure(pressure = "1013.25000 hPa"),
                    awaitItem().barometerState,
                )

                dataSource.emit(Ok(998.5f))
                assertEquals(
                    BarometerState.SuccessToGetPressure(pressure = "998.50000 hPa"),
                    awaitItem().barometerState,
                )
            }
        }

    @Test
    fun `should report a missing sensor when the data source fails`() =
        runTest {
            testPresenter {
                assertEquals(BarometerState.Loading, awaitItem().barometerState)

                dataSource.emit(Err(BarometricPressureError.DeviceDoesNotHaveBarometricSensor()))

                assertEquals(
                    BarometerState.DeviceDoesNotHaveBarometricSensor,
                    awaitItem().barometerState,
                )
            }
        }

    /**
     * Only the sensor is faked. The repository, the use case and the presenter are the real ones,
     * so a break anywhere along that chain fails these tests.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun TestScope.testPresenter(
        block: suspend CircuitReceiveTurbine<BarometerScreen.State>.() -> Unit,
    ) {
        val lifecycleOwner =
            TestLifecycleOwner(
                initialState = Lifecycle.State.RESUMED,
                coroutineDispatcher = UnconfinedTestDispatcher(testScheduler),
            )
        val presenter =
            BarometerPresenter(
                formatPressureUseCase = FormatPressureUseCaseImpl(),
                barometricPressureRepository =
                    BarometricPressureRepositoryImpl(
                        barometricPressureDataSource = dataSource,
                        ioScope = backgroundScope,
                    ),
            )

        presenterTestOf({ withLifecycleOwner(lifecycleOwner) { presenter.present() } }, block = block)
    }
}

/**
 * The presenter collects with `collectAsStateWithLifecycle`, which reads `LocalLifecycleOwner`.
 * Molecule composes without one, so the tests have to supply it.
 *
 * The state is handed back through a [mutableStateOf] rather than a plain variable because
 * `CompositionLocalProvider` puts [content] in its own recomposition scope: reading the holder here
 * is what makes this outer scope recompose — and Molecule re-read the return value — when the
 * presenter emits.
 */
@Composable
private fun <T> withLifecycleOwner(
    lifecycleOwner: LifecycleOwner,
    content: @Composable () -> T,
): T {
    val holder = remember { mutableStateOf<T?>(null) }

    CompositionLocalProvider(LocalLifecycleOwner provides lifecycleOwner) {
        holder.value = content()
    }

    @Suppress("UNCHECKED_CAST")
    return holder.value as T
}
