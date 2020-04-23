package com.example.mynote

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DashboardFragment : Fragment() {
    private lateinit var dashboardFragmentListener: DashboardFragmentListener
    private lateinit var previousButton: Button
    private lateinit var takeANoteButton: Button

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousButton = view.findViewById(R.id.button_previous)
        takeANoteButton = view.findViewById(R.id.button_takeANote)

        dashboardFragmentListener = DashboardFragmentListener(findNavController())

        previousButton.setOnClickListener ( dashboardFragmentListener )
        takeANoteButton.setOnClickListener ( dashboardFragmentListener )
    }
}
