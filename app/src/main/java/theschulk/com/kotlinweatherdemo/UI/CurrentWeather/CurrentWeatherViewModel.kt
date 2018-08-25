package theschulk.com.kotlinweatherdemo.UI.CurrentWeather

import android.arch.lifecycle.ViewModel
import theschulk.com.kotlinweatherdemo.Data.Retrofit.CurrentWeather.CurrentWeatherDataHandler
import theschulk.com.kotlinweatherdemo.Data.Retrofit.CurrentWeather.CurrentWeatherModel
import theschulk.com.kotlinweatherdemo.Data.Retrofit.RetrofitLiveData

class CurrentWeatherViewModel: ViewModel() {

    val liveData : RetrofitLiveData<CurrentWeatherModel.Result> =
            CurrentWeatherDataHandler.INSTANCE.currentWeatherHandler.getWeather()

}