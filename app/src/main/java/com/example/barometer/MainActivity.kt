package com.example.barometer

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.barometer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(
            this, MainViewModel.Factory(getSystemService(Context.SENSOR_SERVICE) as SensorManager)
        )[MainViewModel::class.java]

        viewModel.airPressureLiveData.observe(this) { airPressure ->
            binding.airPressureText.text = "${airPressure.padEnd(10, '0')} hPa"
        }
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