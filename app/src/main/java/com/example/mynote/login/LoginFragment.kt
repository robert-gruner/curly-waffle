package com.example.mynote.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mynote.R
import kotlinx.android.synthetic.main.fragment_login.*

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        LoginController.registerModel(userViewModel)

        userViewModel.getAll.observeOnce(viewLifecycleOwner, Observer {
            if (LoginController.checkUsers(it)) {
                findNavController().navigate(R.id.action_LoginFragment_to_DashboardFragment)
            }
        })

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

            if (LoginController.checkCredentials(inputName, inputPassword)) {
                findNavController().navigate(R.id.action_LoginFragment_to_DashboardFragment)
            }
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        if (!LoginController.isLoggedIn) {
            menu.findItem(R.id.action_logout).isVisible = false;
        }
    }
}
