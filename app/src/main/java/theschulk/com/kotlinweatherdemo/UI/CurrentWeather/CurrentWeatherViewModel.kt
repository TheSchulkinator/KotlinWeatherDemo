package theschulk.com.kotlinweatherdemo.UI.CurrentWeather

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import theschulk.com.kotlinweatherdemo.Data.Retrofit.CurrentWeather.CurrentWeatherModel
import theschulk.com.kotlinweatherdemo.Data.Room.CurrentWeatherEntity
import theschulk.com.kotlinweatherdemo.Data.WeatherRepository

class CurrentWeatherViewModel(private val repo: WeatherRepository?): ViewModel() {

    val weather: LiveData<CurrentWeatherEntity> = repo!!.getWeather()

}