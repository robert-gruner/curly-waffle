package com.example.mynote

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible

class SettingsActivityListener(
    val activity: AppCompatActivity
) : View.OnClickListener, LocationListener {
    private lateinit var locationManager: LocationManager

    companion object {
        val CALL_PERMISSION_REQUEST_CODE = 1
        val FINE_LOCATION_REQUEST_CODE = 2
        val MIN_TIME_TO_REFRESH: Long = 5000L // Millisek bis refreshed wird
        val MIN_DISTANCE_TO_REFRESH: Float = 5F // Meter Distanz bis refreshed wird
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.callHotline -> callHotline()
            R.id.showLocation -> toggleLocation()
            R.id.homepageLink -> openTextAsLink(v as TextView?)
        }
    }

    fun callHotline() {
        val hotlineNumber = activity.getString(R.string.hotlineNumber)
        val intent = Intent(Intent.ACTION_CALL, Uri.parse(hotlineNumber))
        if (ActivityCompat.checkSelfPermission(
                activity, Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permissions = arrayOf(Manifest.permission.CALL_PHONE)
            activity.requestPermissions(permissions, CALL_PERMISSION_REQUEST_CODE)
        } else {
            activity.startActivity(intent)
        }
    }

    fun requestGps() {
        if (ActivityCompat.checkSelfPermission(
                activity, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            activity.requestPermissions(permissions, FINE_LOCATION_REQUEST_CODE)
        } else {
            locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MIN_TIME_TO_REFRESH,
                MIN_DISTANCE_TO_REFRESH,
                this
            )
        }
    }


    override fun onLocationChanged(location: Location?) {
        activity.findViewById<TextView>(R.id.altitudeValue).text = location?.altitude.toString()
        activity.findViewById<TextView>(R.id.latitudeValue).text = location?.latitude.toString()
        activity.findViewById<TextView>(R.id.longitudeValue).text = location?.longitude.toString()
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        Log.d(SettingsActivityListener::class.java.simpleName, "Location status provider changed")
        Log.d(SettingsActivityListener::class.java.simpleName, "Provider: $provider")
        Log.d(SettingsActivityListener::class.java.simpleName, "Status: $status")
    }

    override fun onProviderEnabled(provider: String?) {
        Log.d(SettingsActivityListener::class.java.simpleName, "Location status provider enabled")
        Log.d(SettingsActivityListener::class.java.simpleName, "Provider: $provider")
    }

    override fun onProviderDisabled(provider: String?) {
        Log.d(SettingsActivityListener::class.java.simpleName, "Location status provider disabled")
        Log.d(SettingsActivityListener::class.java.simpleName, "Provider: $provider")
    }

    private fun toggleLocation() {
        val container = activity.findViewById<LinearLayout>(R.id.locationDetails)
        val button = activity.findViewById<Button>(R.id.showLocation)
        container.apply {
            visibility = if (isVisible) View.GONE else View.VISIBLE
            button.text = if (isVisible) activity.getString(R.string.hideLocation) else activity.getString(R.string.showLocation)
            if (isVisible) requestGps() else disableGps()
        }
    }

    private fun disableGps() {
        locationManager.removeUpdates(this)
    }

    private fun openTextAsLink(v: TextView?) {
        var url = v?.text.toString()
        if (!url.startsWith("https://") && !url.startsWith("http://")) {
            url = "http://" + url;
        }
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        activity.startActivity(intent)
    }

}
