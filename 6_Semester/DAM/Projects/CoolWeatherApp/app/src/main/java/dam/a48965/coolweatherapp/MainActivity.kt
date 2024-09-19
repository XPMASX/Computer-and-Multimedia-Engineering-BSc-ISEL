package dam.a48965.coolweatherapp

import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.Location
import android.content.Context;
import android.graphics.drawable.Drawable
import android.location.LocationManager
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import dam.a48965.coolweatherapp.R
import dam.a48965.coolweatherapp.WMO_WeatherCode
import dam.a48965.coolweatherapp.WeatherData
import dam.a48965.coolweatherapp.getWeatherCodeMap
import java.io.InputStreamReader
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Locale


class MainActivity : ComponentActivity() {

    // Location permission request code
    private val locationPermissionRequestCode = 1001

    // Fused Location Provider client
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val PRESSURE_KEY = "pressure"
    private val DIRECTION_KEY = "direction"
    private val SPEED_KEY = "speed"
    private val TEMP_KEY = "temperature"
    private val TIME_KEY = "time"
    private val WEATHERIMAGE_KEY = "weatherImage"
    private val DAY_KEY = "day"

    // Define TextViews as properties of the activity class
    private lateinit var pressure: TextView
    private lateinit var direction: TextView
    private lateinit var speed: TextView
    private lateinit var temp: TextView
    private lateinit var time: TextView
    private lateinit var weatherImage: ImageView
    private lateinit var weatherImageName: String
    private var day: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        savedInstanceState?.let {
            day = it.getBoolean(DAY_KEY, false)
        }


        // Dynamically set the theme based on the time of day
        val dayTheme = if (day) R.style.Theme_Day else R.style.Theme_Night
        val nightTheme = if (day) R.style.Theme_Night else R.style.Theme_Day
        val themeId = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> dayTheme
            Configuration.ORIENTATION_LANDSCAPE -> nightTheme
            else -> dayTheme // Default to day theme if orientation is undefined
        }
        setTheme(themeId)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize TextViews
        pressure = findViewById(R.id.pressureValue)
        direction = findViewById(R.id.directionValue)
        speed = findViewById(R.id.speedValue)
        temp = findViewById(R.id.tempValue)
        time = findViewById(R.id.timeValue)
        weatherImage = findViewById(R.id.weatherImage)

        // Restore the values of TextViews from the saved state
        if (savedInstanceState != null) {
            restoreInstanceState(savedInstanceState)
        } else {

            val isFirstTime = getSharedPreferences("preferences", Context.MODE_PRIVATE)
                .getBoolean("isFirstTime", true)

            // If it's the first time, request location permission
            if (isFirstTime) {
                requestLocationPermission()
            } else {
                // Otherwise, proceed to fetch location
                fetchLocation()
            }
        }

        val fetchWeatherButton: Button = findViewById(R.id.btnUpdate)
        fetchWeatherButton.setOnClickListener { onFetchWeatherButtonClick(it) }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the current values of TextViews to the bundle
        outState.putString(PRESSURE_KEY, pressure.text.toString())
        outState.putString(DIRECTION_KEY, direction.text.toString())
        outState.putString(SPEED_KEY, speed.text.toString())
        outState.putString(TEMP_KEY, temp.text.toString())
        outState.putString(TIME_KEY, time.text.toString())
        outState.putString(WEATHERIMAGE_KEY, weatherImageName)
        outState.putBoolean(DAY_KEY, day)
    }

    private fun restoreInstanceState(savedInstanceState: Bundle) {
        pressure.text = savedInstanceState.getString(PRESSURE_KEY)
        direction.text = savedInstanceState.getString(DIRECTION_KEY)
        speed.text = savedInstanceState.getString(SPEED_KEY)
        temp.text = savedInstanceState.getString(TEMP_KEY)
        time.text = savedInstanceState.getString(TIME_KEY)

        weatherImageName = savedInstanceState.getString(WEATHERIMAGE_KEY).toString()
        val resources = getResources()
        val resID = resources.getIdentifier(weatherImageName, "drawable", getPackageName())
        val drawable = getDrawable(resID)
        weatherImage.setImageDrawable(drawable)

    }


    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            locationPermissionRequestCode
        )
    }

    // Function to handle permission request result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionRequestCode && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission granted, fetch location
            fetchLocation()
        } else {
            Toast.makeText(
                this,
                "Location permission denied. Cannot fetch weather without location.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // Function to fetch location
    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    // Use the retrieved location's latitude and longitude
                    val latitude = location.latitude
                    val longitude = location.longitude

                    // Call the fetchWeatherData function with the retrieved latitude and longitude
                    fetchWeatherData(latitude.toFloat(), longitude.toFloat()).start()
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Failed to fetch location: ${e.message}", e)
                Toast.makeText(
                    this,
                    "Failed to fetch location. Please check your location settings.",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    fun onFetchWeatherButtonClick(view: View) {
        val latEditText: EditText = findViewById(R.id.txtLat)
        val longEditText: EditText = findViewById(R.id.txtLong)

        val latitude = latEditText.text.toString().toFloatOrNull()
        val longitude = longEditText.text.toString().toFloatOrNull()

        if (latitude != null && longitude != null) {
            // Check if latitude is within range (-90 to 90)
            if (latitude in -90.0..90.0) {
                // Check if longitude is within range (-180 to 180)
                if (longitude in -180.0..180.0) {
                    fetchWeatherData(latitude, longitude).start()
                } else {
                    Toast.makeText(this, "Longitude must be between -180 and 180", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Latitude must be between -90 and 90", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Invalid latitude or longitude values.", Toast.LENGTH_SHORT).show()
        }
    }


    private fun WeatherAPI_Call(lat: Float, long: Float): WeatherData? {
        val reqString = buildString {
            append("https://api.open-meteo.com/v1/forecast?")
            append("latitude=${lat}&longitude=${long}&")
            append("current_weather=true&")
            append("hourly=temperature_2m,weathercode,pressure_msl,windspeed_10m&")
            append("daily=sunrise,sunset")
        }
        val url = URL(reqString)
        url.openStream().use {
            return Gson().fromJson(InputStreamReader(it, "UTF-8"), WeatherData::class.java)
        }
    }

    private fun fetchWeatherData(lat: Float, long: Float): Thread {
        val latEditText: EditText = findViewById(R.id.txtLat)
        val longEditText: EditText = findViewById(R.id.txtLong)

        latEditText.setText(lat.toString())
        longEditText.setText(long.toString())

        return Thread {
            val weather = WeatherAPI_Call(lat, long)
            if (weather != null) {
                updateUI(weather)
            }
        }
    }

    private fun updateUI(request: WeatherData) {
        runOnUiThread {


            val pressure: TextView = findViewById(R.id.pressureValue)
            val direction: TextView = findViewById(R.id.directionValue)
            val speed: TextView = findViewById(R.id.speedValue)
            val temp: TextView = findViewById(R.id.tempValue)
            val time: TextView = findViewById(R.id.timeValue)

            pressure.text = request.hourly.pressure_msl[12].toString() + " hPa"
            direction.text = request.current_weather.winddirection.toString()
            speed.text = request.current_weather.windspeed.toString() + " km/h"
            temp.text = request.hourly.temperature_2m[12].toString() + "ºC"
            time.text = request.current_weather.time

            val sunriseTime = request.daily.sunrise[0].substring(11, 16) // Extract time part
            val sunsetTime = request.daily.sunset[0].substring(11, 16) // Extract time part
            val currentTime = request.current_weather.time.substring(11, 16) // Extract time part

            day = isDay(currentTime, sunriseTime, sunsetTime)

            val weatherCodeMap = getWeatherCodeMap()
            val weatherCode = weatherCodeMap[request.current_weather.weathercode]
            weatherImageName = when (weatherCode) {
                WMO_WeatherCode.CLEAR_SKY,
                WMO_WeatherCode.MAINLY_CLEAR,
                WMO_WeatherCode.PARTLY_CLOUDY -> if (day) "${weatherCode?.image}day" else "${weatherCode?.image}night"
                else -> weatherCode?.image.toString()
            }

            val resources = getResources()
            val resID = resources.getIdentifier(weatherImageName, "drawable", getPackageName())
            val drawable = getDrawable(resID)
            weatherImage.setImageDrawable(drawable)
            recreate()
        }
    }

    private fun isDay(currentTime: String, sunriseTime: String, sunsetTime: String): Boolean {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        try {
            val currentDateTime = dateFormat.parse(currentTime)
            val sunriseDateTime = dateFormat.parse(sunriseTime)
            val sunsetDateTime = dateFormat.parse(sunsetTime)

            // Check if current time is between sunrise and sunset
            return currentDateTime.after(sunriseDateTime) && currentDateTime.before(sunsetDateTime)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Default to day if there's an error or current time is not between sunrise and sunset
        return true
    }

}

