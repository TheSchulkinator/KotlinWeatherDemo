package theschulk.com.kotlinweatherdemo.Data.Room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query

/**
 * Create a database access object for database CRUD operations
 */
@Dao
interface CurrentWeatherDAO {
    @Query("SELECT * from currentWeatherTable")
    fun getAll(): List<CurrentWeatherEntity>

    @Insert(onConflict = REPLACE)
    fun insert(weatherData: CurrentWeatherEntity)

    @Query("DELETE from currentWeatherTable")
    fun deleteAll()
}