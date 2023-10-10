# What is the Weather App?
It's a code sample for a clean code that displays the current weather in MVI architecture pattern.

# Requirements
We need to display the current weather based in the current location.

# User Stories
#### 1. As a user I wanna open the weather app, SO THAT I expect to see the weather details.
##### Acceptance criteria
* The user should allow the location permission to be able to get his current location get the weather.
* If the user denied the permission, a message should be displayed to tell the reason of permission required.
* If the user disable the location setting, a message should be displayed to tell the user that the GPS will accelerate detecting the location.
* The weather info should be displayed:
    * The time of detecting the weather info
    * The weather temperature
    * The weather max / weather min and feels like value
    * The pressure value as hpa
    * The wind speed meter per second
    * The humidity percentage 
* If any error happened, a error message show be displayed to the user with a retry button.
* The user should be able to swipe to refresh to reload date from scratch.

# Architecture Pattern
Clean MVI is the architecture pattern of the app.

# Libraries
* Coroutines
* Compose
* 
* Retrofit
* ViewModel
* Hilt
* Kotlin
* Coil
* Mockk
* GMS:play-services
* Compose-destinations (for avoiding boilerplate code in Compose navigation )

# Unit Tests
* Implement unit test for the view-model to test the logic of the app [WeatherViewModel].

# Ui Test ([In-Progress PR](https://github.com/ashrafatef843/WeatherApp/pull/4))
* Weather screen ui test
* End to end test for fetch current weather