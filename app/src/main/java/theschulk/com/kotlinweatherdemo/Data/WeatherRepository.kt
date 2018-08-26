package theschulk.com.kotlinweatherdemo.Data

import android.arch.lifecycle.LiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import theschulk.com.kotlinweatherdemo.Data.Retrofit.CurrentWeather.CurrentWeatherDataHandler
import theschulk.com.kotlinweatherdemo.Data.Retrofit.CurrentWeather.CurrentWeatherModel
import theschulk.com.kotlinweatherdemo.Data.Retrofit.RetrofitLiveData
import theschulk.com.kotlinweatherdemo.Data.Room.CurrentWeatherDAO
import theschulk.com.kotlinweatherdemo.Data.Room.CurrentWeatherEntity
import theschulk.com.kotlinweatherdemo.Data.Room.DbWorkerThread

/**
 * Repo class to decide if data needs to be pulled from the network
 * or if the data in the db is recent enough
 */
class WeatherRepository(private val mWeatherDAO: CurrentWeatherDAO) {

    private lateinit var mDbWorkerThread: DbWorkerThread

    fun getWeather(): LiveData<CurrentWeatherEntity>{
        mDbWorkerThread = DbWorkerThread("dbWorkerThread")
        mDbWorkerThread.start()

        var needNetwork : Boolean = networkNeeded()

        if(!needNetwork){
            retrofitToRoom()
        }
        return mWeatherDAO.getAll()
    }

    private fun networkNeeded(): Boolean{
        mWeatherDAO.getAll() ?: return true

        return false
    }

    private fun retrofitToRoom(){

        var networkWeather: CurrentWeatherModel.Result? = null
        var currentWeatherEntity = CurrentWeatherEntity()

        CurrentWeatherDataHandler.INSTANCE.currentWeatherHandler.getWeather().enqueue(
                object :Callback<CurrentWeatherModel.Result>{
                    override fun onFailure(call: Call<CurrentWeatherModel.Result>?, t: Throwable?) {
                     //implement
                    }

                    override fun onResponse(call: Call<CurrentWeatherModel.Result>?, response: Response<CurrentWeatherModel.Result>?) {
                        networkWeather = response!!.body()
                        currentWeatherEntity.city = networkWeather!!.name
                        currentWeatherEntity.date = networkWeather!!.date
                        currentWeatherEntity.description = networkWeather!!.weather[0].description
                        currentWeatherEntity.icon = networkWeather!!.weather[0].icon
                        currentWeatherEntity.maxTemp = networkWeather!!.main.temp_max
                        currentWeatherEntity.minTemp = networkWeather!!.main.temp_min
                        currentWeatherEntity.temp = networkWeather!!.main.temp

                        val task = Runnable {mWeatherDAO.insert(currentWeatherEntity)}
                        mDbWorkerThread.postTask(task)
                    }
                }
        )

    }

    companion object {

        private val LOCK = Any()
        private var repoInstance: WeatherRepository? = null
        @Synchronized
        fun getInstance(weatherDAO: CurrentWeatherDAO): WeatherRepository? {
            synchronized(LOCK) {
                if (repoInstance == null) {
                    repoInstance = WeatherRepository(weatherDAO)
                }
            }
            return repoInstance
        }
    }
}