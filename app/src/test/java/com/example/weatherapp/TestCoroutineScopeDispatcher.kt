package com.example.weatherapp

import com.example.weatherapp.common.CoroutineScopeDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler

class TestCoroutineScopeDispatcher(
    val testCoroutineScheduler: TestCoroutineScheduler = TestCoroutineScheduler(),
    override val IO: CoroutineDispatcher = StandardTestDispatcher(testCoroutineScheduler),
    override val Main: CoroutineDispatcher = StandardTestDispatcher(testCoroutineScheduler)
) : CoroutineScopeDispatchers {

}