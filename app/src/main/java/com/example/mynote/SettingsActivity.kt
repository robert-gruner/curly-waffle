package com.example.mynote

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

    fun clickHandler(v: View) {
        when (v.id) {
            R.id.btnCallHotline -> {
                val hotlineNumber = getString(R.string.hotlineNumber)
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse(hotlineNumber))
                startActivity(intent)
            }
        }
    }
}
