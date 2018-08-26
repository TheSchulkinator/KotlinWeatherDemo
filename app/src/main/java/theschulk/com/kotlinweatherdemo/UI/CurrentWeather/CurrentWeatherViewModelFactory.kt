package theschulk.com.kotlinweatherdemo.UI.CurrentWeather

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import theschulk.com.kotlinweatherdemo.Data.WeatherRepository

class CurrentWeatherViewModelFactory(private val repo: WeatherRepository?):
        ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(repo) as T
    }
}