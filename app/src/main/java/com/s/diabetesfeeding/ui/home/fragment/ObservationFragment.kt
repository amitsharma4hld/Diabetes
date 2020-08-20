package com.s.diabetesfeeding.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.SymptomsData
import com.s.diabetesfeeding.ui.SymptomsAdapter
import kotlinx.android.synthetic.main.fragment_obgyn.*
import kotlinx.android.synthetic.main.fragment_symptoms.*


class ObservationFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    val Observations = listOf(
        SymptomsData(1,"Vaginal Bleeding",true),
        SymptomsData(2,"Leakage of fluid",false),
        SymptomsData(3,"Fetal movement",false),
        SymptomsData(4,"Contraction",false),
        SymptomsData(5,"Nausea and/or Vomiting",false),
        SymptomsData(6,"Other",false)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ObservationFragment.ARG_PARAM1)
            param2 = it.getString(ObservationFragment.ARG_PARAM2)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showObservation(Observations)

    }

    private fun showObservation(observations: List<SymptomsData>) {
        recyclerViewMonitorBloodGlucose.layoutManager = LinearLayoutManager(activity)
        recyclerViewMonitorBloodGlucose.adapter = ObeservationAdapter(observations)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_observation, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(title: String, bgColorId: Int) =
            ObservationFragment().apply {
                arguments = Bundle().apply {
                    putString(ObservationFragment.ARG_TITLE, title)
                    putInt(ObservationFragment.ARG_BG_COLOR, bgColorId)
                }
            }
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        private const val ARG_TITLE = "arg_title"
        private const val ARG_BG_COLOR = "arg_bg_color"
    }
}