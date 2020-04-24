package com.example.mynote

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class SettingsActivityListener(
    val activity: AppCompatActivity
) : View.OnClickListener {
    val CALL_PERMISSION_REQUEST_CODE = 1

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnCallHotline -> callHotline()
            R.id.homepageLink -> openTextAsLink(v as TextView?)
        }
    }

    private fun openTextAsLink(v: TextView?) {
        var url = v?.text.toString()
        if (!url.startsWith("https://") && !url.startsWith("http://")){
            url = "http://" + url;
        }
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        activity.startActivity(intent)
    }

    fun callHotline() {
        val hotlineNumber = activity.getString(R.string.hotlineNumber)
        val intent = Intent(Intent.ACTION_CALL, Uri.parse(hotlineNumber))
        if (ActivityCompat.checkSelfPermission(
                activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
        ) {
            val permissions = arrayOf(Manifest.permission.CALL_PHONE)
            activity.requestPermissions(permissions, CALL_PERMISSION_REQUEST_CODE)
        } else {
            activity.startActivity(intent)
        }
    }


}
