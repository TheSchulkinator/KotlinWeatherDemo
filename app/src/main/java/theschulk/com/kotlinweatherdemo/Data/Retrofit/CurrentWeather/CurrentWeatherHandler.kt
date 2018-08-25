package theschulk.com.kotlinweatherdemo.Data.Retrofit.CurrentWeather

import theschulk.com.kotlinweatherdemo.BuildConfig
import theschulk.com.kotlinweatherdemo.Data.Retrofit.RetrofitLiveData

class CurrentWeatherHandler(val currentWeatherApi: CurrentWeatherApi) {
    val zip: String = "48393,us"
    //TODO: add your own apiKey here
    val appid: String = BuildConfig.openWeatherApiKey

    fun getWeather(): RetrofitLiveData<CurrentWeatherModel.Result> =
            RetrofitLiveData(currentWeatherApi
                    .getCurrentWeather(zip,
                            "JSON",
                            "imperial",
                            appid))
}