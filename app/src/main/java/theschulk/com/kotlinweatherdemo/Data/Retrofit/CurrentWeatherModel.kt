package theschulk.com.kotlinweatherdemo.Data.Retrofit

/**
 * Class to model the current weather Json response from openweathermap
 * wrap in object to avoid proguard obfuscation
 */
object CurrentWeatherModel {
    data class Main(val main: Main)
    data class Temp(val temp: Int)
    data class TempMin(val temp_min: Int)
    data class TempMax(val temp_max: Int)
}