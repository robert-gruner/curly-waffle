package com.example.mynote

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.invalidateOptionsMenu()

        view.findViewById<Button>(R.id.button_login).setOnClickListener {
            val inputName = editInputUsername.text.toString()
            val inputPassword = editInputPassword.text.toString()

            if (credentialsValid(inputName, inputPassword)) {
                UserController.isLoggedIn = true
                findNavController().navigate(R.id.action_LoginFragment_to_DashboardFragment)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (UserController.isLoggedIn) {
            findNavController().navigate(R.id.action_LoginFragment_to_DashboardFragment)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        if (!UserController.isLoggedIn) {
            menu.findItem(R.id.action_logout).isVisible = false;
        }
    }

    private fun credentialsValid(name: String, password: String): Boolean {
        return true // TODO: Real implementation
    }
}
