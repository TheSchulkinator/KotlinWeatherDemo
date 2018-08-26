package theschulk.com.kotlinweatherdemo.Data.Room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

/**
 * Create the database to hold current weather
 */
@Database(entities = arrayOf(CurrentWeatherEntity::class), version = 1)
abstract class WeatherDatabase : RoomDatabase() {

    //Add DAO to database
    abstract fun currentWeatherDAO(): CurrentWeatherDAO

    //Create a singleton access to the Room DB
    companion object Singleton {
        private var INSTANCE: WeatherDatabase? = null

        fun getInstance(context: Context): WeatherDatabase? {
            if (INSTANCE == null) {
                synchronized(WeatherDatabase::class) {
                Room.databaseBuilder(context.applicationContext,
                        WeatherDatabase::class.java, "weather.db")
                        .build()
            }
        }
            return INSTANCE
    }
        fun destroyInstance(){
            INSTANCE = null
        }
    }
}