package com.s.diabetesfeeding.ui.home.fragment.obgyn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.SymptomsData
import com.s.diabetesfeeding.ui.home.fragment.ObeservationAdapter
import kotlinx.android.synthetic.main.fragment_observation.*
import kotlinx.android.synthetic.main.fragment_symptoms.*
import kotlinx.android.synthetic.main.fragment_symptoms.recyclerViewMonitorBloodGlucose


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

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showObservation(Observations)
        mcv_observation_done.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun showObservation(observations: List<SymptomsData>) {
        recyclerViewMonitorBloodGlucose.layoutManager = LinearLayoutManager(activity)
        recyclerViewMonitorBloodGlucose.adapter =
            ObeservationAdapter(
                observations
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_observation, container, false)
    }

}