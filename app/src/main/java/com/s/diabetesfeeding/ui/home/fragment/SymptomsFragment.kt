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
import kotlinx.android.synthetic.main.fragment_symptoms.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SymptomsFragment : Fragment() {
    val currentDate: String = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault()).format(
        java.util.Date())
    private var param1: String? = null
    private var param2: String? = null
    val symptoms = listOf(
        SymptomsData(1,"Shaky",true),
        SymptomsData(2,"Fast heartbeat",false),
        SymptomsData(3,"Sweating",false),
        SymptomsData(4,"Dizzy",false),
        SymptomsData(5,"Anxious",false),
        SymptomsData(6,"Hungry",false),
        SymptomsData(7,"Blurry Vision",false),
        SymptomsData(8,"Weakness or Fatigue",false),
        SymptomsData(9,"Headche",false),
        SymptomsData(10,"Irritable",false),
        SymptomsData(11,"Other",false)

    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv_today_date_symptoms.text = currentDate
        showSymptoms(symptoms)

    }

    private fun showSymptoms(symptoms: List<SymptomsData>) {
        recyclerViewMonitorBloodGlucose.layoutManager = LinearLayoutManager(activity)
        recyclerViewMonitorBloodGlucose.adapter = SymptomsAdapter(symptoms)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_symptoms, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(title: String, bgColorId: Int) =
            SymptomsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putInt(ARG_BG_COLOR, bgColorId)
                }
            }
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        private const val ARG_TITLE = "arg_title"
        private const val ARG_BG_COLOR = "arg_bg_color"
    }
}