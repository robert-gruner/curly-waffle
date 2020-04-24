package com.example.mynote

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SettingsActivity : AppCompatActivity() {
    private lateinit var callButton: Button
    private lateinit var locationButton: Button
    private lateinit var homepageLink: TextView
    private lateinit var listener: SettingsActivityListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        callButton = findViewById(R.id.callHotline)
        locationButton = findViewById(R.id.showLocation)
        homepageLink = findViewById(R.id.homepageLink)
        listener = SettingsActivityListener(this)
        callButton.setOnClickListener(listener)
        homepageLink.setOnClickListener(listener)
        locationButton.setOnClickListener(listener)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, // Selbst definiert, siehe Konstante
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == listener.CALL_PERMISSION_REQUEST_CODE &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            listener.callHotline()
        }
    }
}
