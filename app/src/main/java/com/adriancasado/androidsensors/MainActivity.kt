package com.adriancasado.androidsensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.adriancasado.androidsensors.databinding.ActivityMainBinding
import kotlin.math.abs

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometre: Sensor
    private lateinit var binding: ActivityMainBinding

    private var xTaps = 0
    private var yTaps = 0
    private var zTaps = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sensorManager = getSystemService(
            Context.SENSOR_SERVICE) as SensorManager
        accelerometre = sensorManager.getDefaultSensor(
            Sensor.TYPE_LINEAR_ACCELERATION) as Sensor
        sensorManager.registerListener(this, accelerometre,
            SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(event: SensorEvent) {
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        binding.xProgressBar.progress = abs(x * 10.0).toInt()
        binding.yProgressBar.progress = abs(y * 10.0).toInt()
        binding.zProgressBar.progress = abs(z * 10.0).toInt()

        if(abs(x * 10.0).toInt() >= 20){
            xTaps++
            binding.xTapCounter.text = "$xTaps taps"
        }

        if(abs(y * 10.0).toInt() >= 20){
            yTaps++
            binding.yTapCounter.text = "$yTaps taps"
        }

        if(abs(z * 10.0).toInt() >= 20){
            zTaps++
            binding.zTapCounter.text = "$zTaps taps"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}
