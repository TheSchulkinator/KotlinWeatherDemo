package theschulk.com.kotlinweatherdemo.Utils

import android.content.Context
import theschulk.com.kotlinweatherdemo.Data.Room.WeatherDatabase
import theschulk.com.kotlinweatherdemo.Data.WeatherRepository
import theschulk.com.kotlinweatherdemo.UI.CurrentWeather.CurrentWeatherViewModelFactory

object InjectorUtils {

    fun provideRepo(context: Context):  WeatherRepository?{
        val db = WeatherDatabase.getInstance(context.applicationContext)
        return WeatherRepository.getInstance(db!!.currentWeatherDAO())
    }

    fun provideCurrentWeatherActivityViewModelFactory(context: Context): CurrentWeatherViewModelFactory {
        val repo = provideRepo(context.applicationContext)
        return CurrentWeatherViewModelFactory(repo)
    }
}