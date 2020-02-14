package com.weatherapp.ui.main

import android.annotation.SuppressLint
import android.content.ContentProviderClient
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.google.android.gms.location.*
import com.weatherapp.R
import com.weatherapp.data.remote.response.WeatherResponse
import com.weatherapp.di.component.ActivityComponent
import com.weatherapp.ui.base.BaseActivity
import com.weatherapp.utils.display.Toaster
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigDecimal
import java.util.*
import java.util.jar.Manifest

class MainActivity : BaseActivity<MainViewModel>() {

    val REQUEST_CODE = 100

    var lati : Double = 0.0
    var long : Double = 0.0
    lateinit var city : String

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest : LocationRequest
    lateinit var locationCallback: LocationCallback

    override fun provideLayoutId(): Int = R.layout.activity_main

    override fun injectDependencies(activityComponent: ActivityComponent) = activityComponent.inject(this)



    override fun setupObservers() {
        super.setupObservers()

        viewModel.weatherStatus.observe(this, Observer {
            if(it == true) {

                viewModel.weatherResponse.observe(this, Observer {

                    val wind : String = it.wind.speed.toString()
                    val temp = (it.main.temp - 273)


                    cityText.setText(it.name)
                    temperatureFeel.setText(it.weather[0].main)
                    moreTextOne.setText("Humidity "+it.main.humidity)
                    moreTextTwo.setText("""Wind ${wind}km/hr""")
                    temperatureText.setText(String.format("%.1f", temp))


                })
            }

        })

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)

        }
        else {
            buildLocationRequest()
            buildLocationCallBack()

            if(ActivityCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.ACCESS_FINE_LOCATION)!=
                    PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED  ){

                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
            }

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper() )

        }
    }

    private fun buildLocationCallBack() {

        locationCallback = object : LocationCallback(){

            override fun onLocationResult(p0: LocationResult?) {
                var location = p0!!.locations.get(p0!!.locations.size-1)
                lati = location.latitude
                long = location.longitude

                val geocoder = Geocoder(applicationContext, Locale.getDefault())
                var addresses: List<Address> = geocoder.getFromLocation(lati,long, 1)
                city = addresses.get(0).locality
                viewModel.getWeather(city)

            }
        }
    }


    override fun setupView(savedInstanceState: Bundle?) {

    }


    private fun buildLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 3000
        locationRequest.smallestDisplacement = 10f
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){

            REQUEST_CODE->{
                if(grantResults.size > 0){

                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                        Toaster.show(this, "Permission Granted")


                    }
                    else
                        Toaster.show(this, "Permission Denied")
                }
            }
        }
    }

    


}






