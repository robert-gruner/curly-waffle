package com.example.mynote

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible

class SettingsActivityListener(
    val activity: AppCompatActivity
) : View.OnClickListener {
    val CALL_PERMISSION_REQUEST_CODE = 1

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

    private fun toggleLocation() {
        val container = activity.findViewById<LinearLayout>(R.id.locationDetails)
        val button = activity.findViewById<Button>(R.id.showLocation)
        container.apply {
            visibility = if (isVisible) View.GONE else View.VISIBLE
            button.text = if (isVisible) activity.getString(R.string.hideLocation) else activity.getString(R.string.showLocation)
        }
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
