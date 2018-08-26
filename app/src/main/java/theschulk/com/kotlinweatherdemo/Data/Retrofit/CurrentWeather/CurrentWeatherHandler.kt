package theschulk.com.kotlinweatherdemo.Data.Retrofit.CurrentWeather

import retrofit2.Call
import theschulk.com.kotlinweatherdemo.BuildConfig
import theschulk.com.kotlinweatherdemo.Data.Retrofit.RetrofitLiveData

class CurrentWeatherHandler(private val currentWeatherApi: CurrentWeatherService) {
    val zip: String = "48393,us"
    //TODO: add your own apiKey here
    val appid: String = BuildConfig.openWeatherApiKey

    fun getWeather(): Call<CurrentWeatherModel.Result> = currentWeatherApi
                    .getCurrentWeather(zip,
                            "JSON",
                            "imperial",
                            appid)
}