package com.s.diabetesfeeding.ui.home.fragment.obgyn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.SymptomsData
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.obgynentities.Observation
import com.s.diabetesfeeding.ui.home.fragment.ObeservationAdapter
import kotlinx.android.synthetic.main.fragment_observation.*
import kotlinx.android.synthetic.main.fragment_symptoms.*
import kotlinx.android.synthetic.main.fragment_symptoms.recyclerViewMonitorBloodGlucose
import kotlinx.coroutines.launch


class ObservationFragment : Fragment() {
    val currentDate: String = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault()).format(
        java.util.Date())

    var observationList: List<Observation> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            observationList =  AppDatabase(requireActivity().applicationContext).getObgynDao().getAllObservation()
            showObservation(observationList)
        }
        mcv_observation_done.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun showObservation(observations: List<Observation>) {
        recyclerViewMonitorBloodGlucose.layoutManager = LinearLayoutManager(activity)
        recyclerViewMonitorBloodGlucose.adapter =
            ObeservationAdapter(requireActivity().applicationContext,observations)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_observation, container, false)
    }

}