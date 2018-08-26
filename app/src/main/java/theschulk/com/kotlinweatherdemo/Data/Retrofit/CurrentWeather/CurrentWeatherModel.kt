package theschulk.com.kotlinweatherdemo.Data.Retrofit.CurrentWeather

/**
 * Class to model the current weather Json response from openweathermap
 * wrap in object to avoid proguard obfuscation
 */
object CurrentWeatherModel {
    data class Result(val main: Main, val weather: List<Weather>, val dt: Long, val name: String)
    data class Main(val temp: Double, val temp_min: Double, val temp_max: Double)
    data class Weather(val description: String, val icon: String)
}