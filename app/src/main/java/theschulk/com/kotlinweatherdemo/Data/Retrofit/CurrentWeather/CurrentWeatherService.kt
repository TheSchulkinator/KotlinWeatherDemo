package theschulk.com.kotlinweatherdemo.Data.Retrofit.CurrentWeather

import android.arch.lifecycle.LiveData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrentWeatherService {

    @GET("weather")
    fun getCurrentWeather(@Query("zip") zip:String ,
                            @Query("mode") JSON: String = "JSON",
                            @Query("units") imperial: String = "imperial",
                            @Query("appid") appid: String): Call<CurrentWeatherModel.Result>
}