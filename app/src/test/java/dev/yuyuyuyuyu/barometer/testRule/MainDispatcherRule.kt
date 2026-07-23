package dev.yuyuyuyuyu.barometer.testRule

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Swaps the Android main dispatcher for a test one.
 *
 * `collectAsStateWithLifecycle` hands off to `repeatOnLifecycle`, which switches to
 * `Dispatchers.Main.immediate`. On the JVM there is no main looper to back that, so any test
 * touching a lifecycle-aware collection needs this rule.
 *
 * `runTest` picks up the scheduler from the main dispatcher, so virtual time stays shared.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
