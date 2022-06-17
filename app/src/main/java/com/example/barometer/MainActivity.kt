package com.example.barometer

import android.content.Context
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.barometer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(
            this, MainViewModel.Factory(getSystemService(Context.SENSOR_SERVICE) as SensorManager)
        )[MainViewModel::class.java]

        viewModel.airPressureLiveData.observe(this, Observer<String> { airPressure ->
            binding.airPressureText.text = "${airPressure} hPa"
        })
    }

    override fun onResume() {
        super.onResume()

        viewModel.registerAirPressureSensor()
    }

    override fun onPause() {
        super.onPause()

        viewModel.unregisterAirPressureSensor()
    }
}