package com.s.diabetesfeeding.ui.home.fragment.diabetes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.prefs
import com.s.diabetesfeeding.ui.home.fragment.*
import com.s.diabetesfeeding.util.getDateFromOffsetDateTime
import kotlinx.android.synthetic.main.fragment_diabetes.*
import kotlinx.android.synthetic.main.fragment_home_screen.tv_blood_glucose
import kotlinx.android.synthetic.main.fragment_home_screen.tv_insulin
import kotlinx.android.synthetic.main.fragment_home_screen.tv_symptoms
import kotlinx.android.synthetic.main.fragment_home_screen.tv_weight
import kotlinx.android.synthetic.main.monitor_blood_glucose_fragment.*
import org.threeten.bp.OffsetDateTime


class  DiabetesFragment : Fragment() {

    val currentDate: String = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault()).format(
        java.util.Date())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_diabetes, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (prefs.getSavedFormattedDate().isNullOrEmpty()){
            tv_today_date_diabetes.text=currentDate
        }else{
            tv_today_date_diabetes.text = prefs.getSavedFormattedDate()
        }
        arguments?.let {
            username_diabetes?.text = "Hello "+DiabetesFragmentArgs.fromBundle(it).name +","
            tv_weeks?.text = DiabetesFragmentArgs.fromBundle(it).weekofpreg.toString()
            tv_doctorname?.text = DiabetesFragmentArgs.fromBundle(it).doctorname
            tv_time?.text = DiabetesFragmentArgs.fromBundle(it).time
        }
        tv_blood_glucose.setOnClickListener {
            val action = DiabetesFragmentDirections.actionDiabetesFragmentToMonitorBloodGlucoseFragment()
            Navigation.findNavController(it).navigate(action)
        }
        tv_insulin.setOnClickListener {
          val action = DiabetesFragmentDirections.actionDiabetesFragmentToInsulinFragment()
            Navigation.findNavController(it).navigate(action)
        }
        tv_weight.setOnClickListener {
            val action = DiabetesFragmentDirections.actionDiabetesFragmentToWeightFragment()
            Navigation.findNavController(it).navigate(action)
        }
        tv_symptoms.setOnClickListener {
           val action = DiabetesFragmentDirections.actionDiabetesFragmentToSymptomsFragment()
            Navigation.findNavController(it).navigate(action)
        }
        tv_progress.setOnClickListener {
            val action = DiabetesFragmentDirections.actionDiabetesFragmentToProgressBloodGlucoseFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }


}