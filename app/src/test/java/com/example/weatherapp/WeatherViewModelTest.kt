package com.example.weatherapp

import com.example.weatherapp.common.presentation.dto.Fail
import com.example.weatherapp.common.presentation.dto.Loading
import com.example.weatherapp.common.presentation.dto.Success
import com.example.weatherapp.data.location.LocationStatus
import com.example.weatherapp.data.location.Location
import com.example.weatherapp.data.dto.CurrentWeather
import com.example.weatherapp.domain.CurrentWeatherUseCase
import com.example.weatherapp.domain.location.LocationTracker
import com.example.weatherapp.presentation.WeatherViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class WeatherViewModelTest {

    private lateinit var weatherViewModel: WeatherViewModel

    @MockK
    private lateinit var locationTracker: LocationTracker

    @MockK
    private lateinit var weatherUseCase: CurrentWeatherUseCase

    @MockK
    private lateinit var currentWeather: CurrentWeather
    private val testCoroutineScopeDispatcher = TestCoroutineScopeDispatcher()

    @Before
    fun init() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `get current weather WHEN location tracker returns permission denied EXPECTED submit permission denied`() {
        // Prepare
        coEvery { locationTracker.getCurrentLocation() } returns LocationStatus.LocationPermissionDenied

        //Execute
        // init viewmodel invoke getLocation in the init block
        weatherViewModel =
            WeatherViewModel(weatherUseCase, locationTracker, testCoroutineScopeDispatcher)

        assertTrue(weatherViewModel.state.locationStatus is LocationStatus.LocationLoading)
        testCoroutineScopeDispatcher.testCoroutineScheduler.runCurrent()
        assertTrue(weatherViewModel.state.locationStatus is LocationStatus.LocationPermissionDenied)
    }

    @Test
    fun `get current weather WHEN location tracker returns location setting disabled EXPECTED submit location setting disabled`() {
        // Prepare
        coEvery { locationTracker.getCurrentLocation() } returns LocationStatus.LocationSettingDisabled

        //Execute
        // init viewmodel invoke getLocation in the init block
        weatherViewModel =
            WeatherViewModel(weatherUseCase, locationTracker, testCoroutineScopeDispatcher)

        assertTrue(weatherViewModel.state.locationStatus is LocationStatus.LocationLoading)
        testCoroutineScopeDispatcher.testCoroutineScheduler.runCurrent()
        assertTrue(weatherViewModel.state.locationStatus is LocationStatus.LocationSettingDisabled)
    }

    @Test
    fun `get current weather WHEN location tracker returns location undetectable EXPECTED submit location undetectable`() {
        // Prepare
        coEvery { locationTracker.getCurrentLocation() } returns LocationStatus.LocationUndetectable

        //Execute
        // init viewmodel invoke getLocation in the init block
        weatherViewModel =
            WeatherViewModel(weatherUseCase, locationTracker, testCoroutineScopeDispatcher)

        assertTrue(weatherViewModel.state.locationStatus is LocationStatus.LocationLoading)
        testCoroutineScopeDispatcher.testCoroutineScheduler.runCurrent()
        assertTrue(weatherViewModel.state.locationStatus is LocationStatus.LocationUndetectable)
    }

    @Test
    fun `get current weather WHEN location tracker returns location EXPECTED submit location`() {
        // Prepare
        coEvery { locationTracker.getCurrentLocation() } returns LocationStatus.LocationDetected(
            Location(1.0, 1.0)
        )

        //Execute
        // init viewmodel invoke getLocation in the init block
        weatherViewModel =
            WeatherViewModel(weatherUseCase, locationTracker, testCoroutineScopeDispatcher)

        assertTrue(weatherViewModel.state.locationStatus is LocationStatus.LocationLoading)
        testCoroutineScopeDispatcher.testCoroutineScheduler.runCurrent()
        assertTrue(weatherViewModel.state.locationStatus is LocationStatus.LocationDetected)
    }

    @Test
    fun `get current weather WHEN location detected and repo return success EXPECTED submit weather`() {
        coEvery { weatherUseCase(1.0, 1.0) } returns currentWeather

        `get current weather WHEN location tracker returns location EXPECTED submit location`()

        assertTrue(weatherViewModel.state.currentWeather is Success)
        assertEquals(currentWeather, weatherViewModel.state.currentWeather())
    }

    @Test
    fun `get current weather WHEN location detected repo return error EXPECTED submit error`() {
        coEvery { weatherUseCase(1.0, 1.0) } throws UnknownError()

        `get current weather WHEN location tracker returns location EXPECTED submit location`()

        assertTrue(weatherViewModel.state.currentWeather is Fail)
        assertTrue((weatherViewModel.state.currentWeather as Fail).error is UnknownError)
    }
}
