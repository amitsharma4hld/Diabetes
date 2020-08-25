package com.s.diabetesfeeding.ui.home.fragment.obgyn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.s.diabetesfeeding.R
import kotlinx.android.synthetic.main.fragment_prental_visit.*


class PrentalVisitFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mcv_prental_done.setOnClickListener {
            requireActivity().onBackPressed()
        }
        totalnumber.setOnClickListener {
            val action = PrentalVisitFragmentDirections.actionPrentalVisitFragmentToPrePregnancyInputFragment()
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_prental_visit, container, false)
    }

}