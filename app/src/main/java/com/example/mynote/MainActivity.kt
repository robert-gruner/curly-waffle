package com.example.mynote

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initFab()
    }

    override fun onResume() {
        super.onResume()
        initFab()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            R.id.action_logout -> {
                LoginController.isLoggedIn = false
                findNavController(R.id.nav_host_fragment).navigate(R.id.action_DashboardFragment_to_LoginFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initFab() {
        fab.setOnClickListener { view ->
            findNavController(R.id.nav_host_fragment).navigate(R.id.noteDetailActivity)
        }
        LoginController.registerHideableElement(fab)
    }
}
