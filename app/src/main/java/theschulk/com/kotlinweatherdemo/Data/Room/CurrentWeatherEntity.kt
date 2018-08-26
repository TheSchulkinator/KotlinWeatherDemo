package theschulk.com.kotlinweatherdemo.Data.Room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


/**
 *Class to create the structure of a table to hold the most recent weather data
 */
@Entity(tableName = "currentWeatherTable")
class CurrentWeatherEntity(
        @PrimaryKey(autoGenerate = true) var id: Long?,
        @ColumnInfo(name="date") var date : Long,
        @ColumnInfo(name = "temp") var temp: Int,
        @ColumnInfo(name="min_temp") var minTemp: Int,
        @ColumnInfo(name="max_temp") var maxTemp: Int,
        @ColumnInfo(name="description") var description: String,
        @ColumnInfo(name="icon") var icon: String?,
        @ColumnInfo(name="city") var city: String
) {
    constructor():this(null, 0, 0,0,0,"",
            "", "")
}