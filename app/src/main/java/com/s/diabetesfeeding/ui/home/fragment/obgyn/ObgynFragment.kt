package com.s.diabetesfeeding.ui.home.fragment.obgyn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.s.diabetesfeeding.R
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.obgynentities.PrentalVisitRecord
import com.s.diabetesfeeding.ui.home.fragment.diabetes.DiabetesFragmentArgs
import kotlinx.android.synthetic.main.fragment_obgyn.*
import kotlinx.coroutines.launch


class ObgynFragment : Fragment() {

    val currentDate: String = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault()).format(
        java.util.Date())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_obgyn, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv_today_date_obgyn.text = currentDate

        val prentalVisitRecordList = listOf(
            PrentalVisitRecord("Pre-pregnancy weight", "200", "lb"),
            PrentalVisitRecord("Height","","Inch"),
            PrentalVisitRecord("BMI","200","lb"),
            PrentalVisitRecord("Blood Pressure","","lb"),
            PrentalVisitRecord("Fundal Height/EGA","","lb"),
            PrentalVisitRecord("Fetal heart rate","","lb"),
            PrentalVisitRecord("Weight","","lb"),
            PrentalVisitRecord("Glucose Level","","lb"),
            PrentalVisitRecord("Protein","","lb"),
            PrentalVisitRecord("Urine","","lb"),
            PrentalVisitRecord("Significant Findings","","lb"),
            PrentalVisitRecord("Instructions","","lb")
        )

        viewLifecycleOwner.lifecycleScope.launch {
            context?.let {
                if (AppDatabase(it).getObgynDao().getAllPrentalVisitRecord().isNullOrEmpty()) {
                    AppDatabase(it).getObgynDao().saveAllPrentalVisitRecord(prentalVisitRecordList)
                }
            }
        }

        arguments?.let {
            username_obgy?.text = "Hello "+ DiabetesFragmentArgs.fromBundle(it).name +","
            tv_week_obyn?.text = DiabetesFragmentArgs.fromBundle(it).weekofpreg.toString()
            tv_doctorname_obgyn?.text = DiabetesFragmentArgs.fromBundle(it).doctorname
            tv_time_obgyn?.text = DiabetesFragmentArgs.fromBundle(it).time
        }
        mc_observation.setOnClickListener {
            val action = ObgynFragmentDirections.actionObgynFragmentToObservationFragment()
            Navigation.findNavController(it).navigate(action)
        }
        mc_counseling.setOnClickListener {
            val action = ObgynFragmentDirections.actionObgynFragmentToCounselingFragment()
            Navigation.findNavController(it).navigate(action)
        }
        mc_prenatal_visit.setOnClickListener {
            val action = ObgynFragmentDirections.actionObgynFragmentToPrentalVisitFragment()
            Navigation.findNavController(it).navigate(action)
        }

    }


}