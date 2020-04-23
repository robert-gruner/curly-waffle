package com.example.mynote

import android.view.View
import androidx.navigation.NavController

class DashboardFragmentListener(
    val navController: NavController
) : View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_previous -> navController.navigate(R.id.action_DashboardFragment_to_LoginFragment)
            R.id.button_takeANote -> navController.navigate(R.id.noteDetailActivity)
        }
    }

}
