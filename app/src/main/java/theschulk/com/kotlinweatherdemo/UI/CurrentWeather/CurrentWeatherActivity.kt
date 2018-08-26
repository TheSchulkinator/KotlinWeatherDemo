package theschulk.com.kotlinweatherdemo.UI.CurrentWeather

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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

                val CurrentTempTextView = findViewById<TextView>(R.id.tv_current_temp)

                CurrentTempTextView.text = resultString
            }
        })


    }
}
