package theschulk.com.kotlinweatherdemo.UI.CurrentWeather

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import theschulk.com.kotlinweatherdemo.Data.Retrofit.CurrentWeather.CurrentWeatherModel
import theschulk.com.kotlinweatherdemo.Data.Room.CurrentWeatherEntity
import theschulk.com.kotlinweatherdemo.R
import theschulk.com.kotlinweatherdemo.Utils.InjectorUtils

class CurrentWeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_weather)

        //Create View Model Factory and view model
        val factory = InjectorUtils.provideCurrentWeatherActivityViewModelFactory(this)
        val mViewModel = ViewModelProviders.of(this, factory).get(CurrentWeatherViewModel::class.java)

        mViewModel.weather.observe(this, Observer<CurrentWeatherEntity> { response ->
            if (response != null) {
                val resultString: String = response.temp.toString()

                val weatherIconImageView = findViewById<ImageView>(R.id.iv_weather_icon)
                val currentTempTextView = findViewById<TextView>(R.id.tv_current_temp)
                val highTempTextView = findViewById<TextView>(R.id.tv_high_temp)
                val lowTempTextView = findViewById<TextView>(R.id.tv_low_temp)
                val cityNameTextView = findViewById<TextView>(R.id.tv_city)
                val descriptionTextView = findViewById<TextView>(R.id.tv_weather_description)

                weatherIconImageView.setImageResource(applicationContext.resources.getIdentifier(response.icon.toString(),
                        "Drawable", applicationContext.packageName))
                currentTempTextView.text = resultString + getString(R.string.temp_suffix)
                highTempTextView.text = getString(R.string.high) + response.maxTemp.toString() + getString(R.string.temp_suffix)
                lowTempTextView.text = getString(R.string.high) + response.minTemp.toString() + getString(R.string.temp_suffix)
                cityNameTextView.text = response.city
                descriptionTextView.text = response.description
            }
        })


    }
}
