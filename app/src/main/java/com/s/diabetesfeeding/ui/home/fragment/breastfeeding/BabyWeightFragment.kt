package com.s.diabetesfeeding.ui.home.fragment.breastfeeding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.ui.home.fragment.HomeScreenFragment
import kotlinx.android.synthetic.main.fragment_baby_weight.*


class BabyWeightFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        done.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_baby_weight, container, false)
    }


}