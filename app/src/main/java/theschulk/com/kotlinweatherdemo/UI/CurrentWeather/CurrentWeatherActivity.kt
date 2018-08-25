package theschulk.com.kotlinweatherdemo.UI.CurrentWeather

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import theschulk.com.kotlinweatherdemo.Data.Retrofit.CurrentWeather.CurrentWeatherModel
import theschulk.com.kotlinweatherdemo.R

class CurrentWeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_weather)

        ViewModelProviders.of(this)
                .get(CurrentWeatherViewModel::class.java)
                .liveData.observe(this, Observer<CurrentWeatherModel.Result> { response ->
            if (response != null) {
                val resultString: String = response.main.temp.toString()

                val CurrentTempTextView = findViewById<TextView>(R.id.tv_current_temp)

                CurrentTempTextView.text = resultString
            }
        })


    }
}
