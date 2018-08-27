package theschulk.com.kotlinweatherdemo.Data

import android.arch.lifecycle.LiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import theschulk.com.kotlinweatherdemo.Data.Retrofit.CurrentWeather.CurrentWeatherDataHandler
import theschulk.com.kotlinweatherdemo.Data.Retrofit.CurrentWeather.CurrentWeatherModel
import theschulk.com.kotlinweatherdemo.Data.Room.CurrentWeatherDAO
import theschulk.com.kotlinweatherdemo.Data.Room.CurrentWeatherEntity
import theschulk.com.kotlinweatherdemo.Data.Room.DbWorkerThread

/**
 * Repo class to decide if data needs to be pulled from the network
 * or if the data in the db is recent enough
 */
class WeatherRepository(private val mWeatherDAO: CurrentWeatherDAO,
                        private  val mDbWorkerThread: DbWorkerThread) {

    fun getWeather(): LiveData<CurrentWeatherEntity>{
        var needNetwork : Boolean = networkNeeded()

        if(!needNetwork){
            retrofitToRoom()
        } else if (checkAgeOfData()){
            //Delete data if the data is older than 30 minutes
            val task = Runnable { mWeatherDAO.deleteAll() }
            mDbWorkerThread.postTask(task)

            //Add new data to db
            retrofitToRoom()
        }
        return mWeatherDAO.getAllLiveData()
    }

    private fun networkNeeded(): Boolean{
        var weatherData : CurrentWeatherEntity? = null
        val task = Runnable { weatherData = mWeatherDAO.getAll() }
        mDbWorkerThread.postTask(task)
        if(weatherData != null) {
            return true
        }else{
            return false
        }
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
                        currentWeatherEntity.date = networkWeather!!.dt
                        currentWeatherEntity.description = networkWeather!!.weather[0].description
                        currentWeatherEntity.icon = "ic_" + networkWeather!!.weather[0].icon
                        currentWeatherEntity.maxTemp = networkWeather!!.main.temp_max
                        currentWeatherEntity.minTemp = networkWeather!!.main.temp_min
                        currentWeatherEntity.temp = networkWeather!!.main.temp

                        val task = Runnable {mWeatherDAO.insert(currentWeatherEntity)}
                        mDbWorkerThread.postTask(task)
                    }
                }
        )

    }

    /**
     * Fun to check to see how old the data is
     */
    fun checkAgeOfData(): Boolean{

        var dbWeather : CurrentWeatherEntity? = null
        val task = Runnable { dbWeather = mWeatherDAO.getAll() }
        mDbWorkerThread.postTask(task)


        val dbSavedUnixDate = dbWeather!!.date
        val currentUnixDate = System.currentTimeMillis() / 1000L

        //see if the difference of the two dates is greater than 30 minutes
        val differenceUnixDate = currentUnixDate - dbSavedUnixDate

        return differenceUnixDate >=  1800
    }

    companion object {

        private val LOCK = Any()
        private var repoInstance: WeatherRepository? = null
        @Synchronized
        fun getInstance(weatherDAO: CurrentWeatherDAO,
                        DbWorkerThread: DbWorkerThread): WeatherRepository? {
            synchronized(LOCK) {
                if (repoInstance == null) {
                    repoInstance = WeatherRepository(weatherDAO, DbWorkerThread)
                }
            }
            return repoInstance
        }
    }
}