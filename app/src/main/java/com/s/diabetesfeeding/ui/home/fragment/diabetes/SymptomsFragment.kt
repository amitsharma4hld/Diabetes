package com.s.diabetesfeeding.ui.home.fragment.diabetes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.SymptomsData
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.BloodGlucoseCategoryItem
import com.s.diabetesfeeding.data.db.entities.CategoryWithItems
import com.s.diabetesfeeding.ui.adapter.SymptomsAdapter
import com.s.diabetesfeeding.util.Coroutines
import com.s.diabetesfeeding.util.alertDialog
import kotlinx.android.synthetic.main.fragment_symptoms.*
import kotlinx.coroutines.launch


class SymptomsFragment : Fragment() {
    val currentDate: String = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault()).format(
        java.util.Date())

    var symptomsList: List<SymptomsData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv_today_date_symptoms.text = currentDate

        mcv_symptoms_done.setOnClickListener {
            requireActivity().onBackPressed()
        }
        tv_what_can_you_do.setOnClickListener {
           requireActivity().alertDialog("title","message")
        }

        viewLifecycleOwner.lifecycleScope.launch {
            symptomsList =  AppDatabase(requireActivity().applicationContext).getSymptomsDao().getSymptom()
            showSymptoms(symptomsList)
        }

    }

   private fun showSymptoms(symptoms: List<SymptomsData>) {
        recyclerViewMonitorBloodGlucose.layoutManager = LinearLayoutManager(activity)
        recyclerViewMonitorBloodGlucose.adapter =
            SymptomsAdapter(requireActivity().applicationContext,symptoms)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_symptoms, container, false)
    }

}