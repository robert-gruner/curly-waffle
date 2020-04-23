package com.example.mynote

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

    fun clickHandler(v: View) {
        when (v.id) {
            R.id.btnCallHotline -> callHotline()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
       if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
           callHotline()
       }
    }

    private fun callHotline() {
        val hotlineNumber = getString(R.string.hotlineNumber)
        val intent = Intent(Intent.ACTION_CALL, Uri.parse(hotlineNumber))
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
        ) {
            val permissions = arrayOf(Manifest.permission.CALL_PHONE)
            requestPermissions(permissions, 1)
        } else {
            startActivity(intent)
        }
    }
}
