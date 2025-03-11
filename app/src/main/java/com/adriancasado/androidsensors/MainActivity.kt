package com.adriancasado.androidsensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.adriancasado.androidsensors.databinding.ActivityMainBinding
import kotlin.math.abs

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometre: Sensor
    private lateinit var binding: ActivityMainBinding
    private var counter: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Utilitzem bindings per facilitar accés als elements gràfics
        // recorda activar-los a build.gradle.kts afegint:
        // viewBinding { enable = true }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // escoltar variacions dels sensors
        sensorManager = getSystemService(
            Context.SENSOR_SERVICE) as SensorManager
        accelerometre = sensorManager.getDefaultSensor(
            Sensor.TYPE_LINEAR_ACCELERATION) as Sensor
        sensorManager.registerListener(this, accelerometre,
            SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(event: SensorEvent) {
        // emprem les dades del sensor
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        Log.i("TEST", z.toString())
        if(abs(z) > 2) {
            counter++
            Toast.makeText(this, "Tap recibido", Toast.LENGTH_SHORT).show()
        }

        binding.counter.text = counter.toString();
        // 1g = 9,8 m/s² , què és un valor força alt.
        // Al fer *10 ens acostem als 100, que és el valor màxim per defecte de la ProgressBar
        binding.xProgressBar.progress = abs(x*10.0).toInt()
        binding.yProgressBar.progress = abs(y*10.0).toInt()
        binding.zProgressBar.progress = abs(z*10.0).toInt()
    }

    override fun onSaveInstanceState(outState: Bundle) {

        // ASEGURAR QUE NO CAMBIAN LOS VALORES AL GIRAR LA PANTALLA
        // outState?.run {
        //     putInt(STATE_SCORE, currentScore)
        //     putInt(STATE_LEVEL, currentLevel)
        // }
        super.onSaveInstanceState(outState)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // TODO("Not yet implemented")
    }
}