package theschulk.com.kotlinweatherdemo.Data.Retrofit.CurrentWeather

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CurrentWeatherDataHandler {

    //Create a single instance of the CurrentWeatherDataHandler
    companion object Singleton{
        val INSTANCE: CurrentWeatherDataHandler by lazy{CurrentWeatherDataHandler()}

        const val BASE_URL = "http://api.openweathermap.org/data/2.5/"
    }

    val currentWeatherHandler: CurrentWeatherHandler

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        currentWeatherHandler = CurrentWeatherHandler(retrofit.create(CurrentWeatherService::class.java))
    }
}